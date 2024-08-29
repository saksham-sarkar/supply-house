package com.supplyhouse.orchestration.service;

import com.supplyhouse.orchestration.client.AccountServiceClient;
import com.supplyhouse.orchestration.client.InvitationServiceClient;
import com.supplyhouse.orchestration.client.OrderServiceClient;
import com.supplyhouse.orchestration.exception.OrchestrationServiceException;
import com.supplyhouse.orchestration.model.dto.AccountDTO;
import com.supplyhouse.orchestration.model.dto.InvitationDTO;
import com.supplyhouse.orchestration.model.dto.LinkSubAccountDTO;
import com.supplyhouse.orchestration.model.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrchestrationService {

    @Autowired
    private AccountServiceClient accountServiceClient;

    @Autowired
    private OrderServiceClient orderServiceClient;

    @Autowired
    private InvitationServiceClient invitationServiceClient;

    public String upgradeAccountToBusinessOwner(String accountId) {
        // Check if the account has placed 10 orders
        boolean hasPlacedTenOrders = orderServiceClient.hasPlacedTenOrders(accountId);
        if (hasPlacedTenOrders) {
            return accountServiceClient.upgradeAccountToBusinessOwner(accountId);
        }
        throw new OrchestrationServiceException("Account with accountId = " + accountId + " " +
                "do not have enough orders.Upgrade failed");
    }

    public String sendInvitation(String businessOwnerId, String subAccountId) {
        // Check if the subaccount is already linked to another business owner
        boolean isLinked = accountServiceClient.isSubaccountLinked(subAccountId);
        if (isLinked) {
            throw new OrchestrationServiceException("Subaccount with accountId = " + subAccountId +
                    " is already linked to another business owner.");
        }

        AccountDTO businessOwnerAccount = accountServiceClient.getAccount(String.valueOf(businessOwnerId));
        if(!businessOwnerAccount.isBusinessOwner()) {
            throw new OrchestrationServiceException("Account with accountId = " + businessOwnerId +
                    " is not a BusinessOwner");
        }
        AccountDTO subAccount = accountServiceClient.getAccount(String.valueOf(subAccountId));

        InvitationDTO newInvitationDTO = InvitationDTO.builder()
                .businessOwnerId(String.valueOf(businessOwnerId))
                .subAccountId(String.valueOf(subAccountId))
                .senderEmail(businessOwnerAccount.getEmail())
                .recipientEmail(subAccount.getEmail()).build();
        String invitationToken = invitationServiceClient.sendNewInvitation(newInvitationDTO);
        newInvitationDTO.setInvitationToken(String.valueOf(invitationToken));
        return invitationToken;
    }

    public String respondToInvitation(String subAccountId, String invitationToken, boolean accept,
                                    boolean shareFullHistory) {
        // Find the invitation
        InvitationDTO invitationDTO = invitationServiceClient.getInvitationByToken(invitationToken);

        if (invitationDTO == null) {
            throw new OrchestrationServiceException("Invalid invitation token = " + invitationToken);
        }

        String invitationStatusMessage;
        if(accept) {
            invitationStatusMessage = acceptInvitation(invitationToken);
            // Link the subaccount to the business owner
            LinkSubAccountDTO linkSubAccountDTO = new LinkSubAccountDTO(subAccountId,
                    String.valueOf(invitationDTO.getBusinessOwnerId()), shareFullHistory);
            accountServiceClient.linkSubaccountToBusinessOwner(linkSubAccountDTO);
        } else {
            invitationStatusMessage = declineInvitation(invitationToken);
        }
        return invitationStatusMessage;
    }

    public List<OrderDTO> getSubAccountOrderHistory(String businessOwnerId, String subAccountId) {
        AccountDTO businessOwnerAccount = accountServiceClient.getAccount(businessOwnerId);
        AccountDTO subAccount = accountServiceClient.getAccount(subAccountId);
        if (subAccount.getBusinessOwnerAccountId()==null ||
                !subAccount.getBusinessOwnerAccountId().equals(businessOwnerAccount.getAccountId())) {
            throw new OrchestrationServiceException("Business Owner Account with accountId = " + businessOwnerId +
                    " not Linked with subaccount with accountId = " + subAccountId);
        }
        if (subAccount.isShareFullHistory()) {
            // Fetch all orders for the subaccount
            return orderServiceClient.fetchAll(subAccount.getAccountId());
        } else {
            // Fetch orders for the subaccount only from the date it was linked to the business owner
            String orderDate = subAccount.getSubAccountLinkedDate();
            return orderServiceClient.fetchByDate(subAccount.getAccountId(), orderDate);
        }
    }

    public String unlinkSubAccount(String businessOwnerId, String subAccountId) {
        AccountDTO businessOwnerAccount = accountServiceClient.getAccount(businessOwnerId);
        AccountDTO subAccount = accountServiceClient.getAccount(subAccountId);
        if (businessOwnerAccount.getSubAccountIds().isEmpty() ||
                !subAccount.getBusinessOwnerAccountId().equals(businessOwnerAccount.getAccountId())) {
            throw new OrchestrationServiceException("Business Owner Account with accountId = " + businessOwnerId +
                    " not Linked with subaccount with accountId = " + subAccountId);
        }
        return accountServiceClient.unlinkSubaccount(subAccountId);
    }

    private String acceptInvitation(String token) {
        return invitationServiceClient.acceptInvitation(token);
    }

    private String declineInvitation(String token) {
        return invitationServiceClient.declineInvitation(token);
    }

    public AccountDTO saveAccount(AccountDTO accountDTO) {
        return accountServiceClient.save(accountDTO);
    }

    public void saveOrder(OrderDTO orderDTO) {
        Long orderId = orderServiceClient.save(orderDTO);
        orderDTO.setOrderId(String.valueOf(orderId));
    }

    public InvitationDTO findByToken(String invitationToken) {
        return invitationServiceClient.getInvitationByToken(invitationToken);
    }
}

