package com.supplyhouse.orchestration.integrationtests;

import com.supplyhouse.orchestration.model.dto.AccountDTO;
import com.supplyhouse.orchestration.model.dto.InvitationDTO;
import com.supplyhouse.orchestration.model.dto.OrderDTO;
import com.supplyhouse.orchestration.service.OrchestrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrchestrationServiceIntegrationTests {

    @Autowired
    private OrchestrationService orchestrationService;

    private AccountDTO businessOwner;
    private AccountDTO subAccount1;
    private AccountDTO subAccount2;

    @BeforeEach
    public void setup() {
        // Create and save Business Owner
        businessOwner = AccountDTO.builder()
                .email("businessOwner1Email")
//                .isBusinessOwner(true)
                .build();
        businessOwner = orchestrationService.saveAccount(businessOwner);

//        // Create and save SubAccounts
        subAccount1 = AccountDTO.builder()
                .email("subAccount1Email")
                .isBusinessOwner(false)
                .build();
        subAccount1 = orchestrationService.saveAccount(subAccount1);

        subAccount2 =  AccountDTO.builder()
                .email("subAccount2Email")
                .isBusinessOwner(false)
                .build();
        subAccount2 = orchestrationService.saveAccount(subAccount2);

//        // Create and save Orders for Business Owner and SubAccounts
        for (int i = 0; i < 15; i++) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setAccountId(businessOwner.getAccountId());
            orchestrationService.saveOrder(orderDTO);
        }

        for (int i = 0; i < 5; i++) {
            OrderDTO orderDTO1 = new OrderDTO();
            orderDTO1.setAccountId(subAccount1.getAccountId());
            orchestrationService.saveOrder(orderDTO1);
        }

        for (int i = 0; i < 7; i++) {
            OrderDTO orderDTO2 = new OrderDTO();
            orderDTO2.setAccountId(subAccount2.getAccountId());
            orchestrationService.saveOrder(orderDTO2);
        }
    }

    @Test
    public void testRequestUpgradeToBusinessOwner() {
        String result = orchestrationService.upgradeAccountToBusinessOwner(businessOwner.getAccountId());
        assertThat(result).isEqualTo("Account upgraded successfully");
    }

    @Test
    public void testSendInvitation() {
        orchestrationService.upgradeAccountToBusinessOwner(businessOwner.getAccountId());
        String invitationTokenGenerated = orchestrationService.sendInvitation(businessOwner.getAccountId(), subAccount1.getAccountId());
        assertThat(invitationTokenGenerated).isNotNull();

        InvitationDTO invitationDTO = orchestrationService.findByToken(invitationTokenGenerated);
        assertThat(invitationDTO).isNotNull();
        assertThat(invitationDTO.getStatus()).isEqualTo("PENDING");
    }

    @Test
    public void testAcceptInvitationAndShareFullHistory() {
        orchestrationService.upgradeAccountToBusinessOwner(businessOwner.getAccountId());
        String invitationTokenGenerated = orchestrationService.sendInvitation(businessOwner.getAccountId(), subAccount1.getAccountId());

        InvitationDTO invitationDTO = orchestrationService.findByToken(invitationTokenGenerated);
        String result =  orchestrationService.respondToInvitation(subAccount1.getAccountId(),
                invitationDTO.getInvitationToken(), true, true);
        assertThat(result).isEqualTo("Invitation accepted!");

        InvitationDTO updatedInvitation = orchestrationService.findByToken(invitationDTO.getInvitationToken());
        assertThat(updatedInvitation).isNotNull();
        assertThat(updatedInvitation.getStatus()).isEqualTo("ACCEPTED");
    }

    @Test
    public void testAcceptInvitationAndSharePartialHistory() {
        orchestrationService.upgradeAccountToBusinessOwner(businessOwner.getAccountId());
        String invitationTokenGenerated = orchestrationService.sendInvitation(businessOwner.getAccountId(), subAccount1.getAccountId());

        InvitationDTO invitationDTO = orchestrationService.findByToken(invitationTokenGenerated);
        String result =  orchestrationService.respondToInvitation(subAccount1.getAccountId(),
                invitationDTO.getInvitationToken(), true, false);
        assertThat(result).isEqualTo("Invitation accepted!");

        InvitationDTO updatedInvitation = orchestrationService.findByToken(invitationDTO.getInvitationToken());
        assertThat(updatedInvitation).isNotNull();
        assertThat(updatedInvitation.getStatus()).isEqualTo("ACCEPTED");
    }

    @Test
    public void testDeclineInvitation() {
        orchestrationService.upgradeAccountToBusinessOwner(businessOwner.getAccountId());
        String invitationTokenGenerated = orchestrationService.sendInvitation(businessOwner.getAccountId(), subAccount1.getAccountId());

        InvitationDTO invitationDTO = orchestrationService.findByToken(invitationTokenGenerated);
        String result = orchestrationService.respondToInvitation(subAccount1.getAccountId(),
                invitationDTO.getInvitationToken(), false, false);
        assertThat(result).isEqualTo("Invitation declined!");

        InvitationDTO updatedInvitation = orchestrationService.findByToken(invitationDTO.getInvitationToken());
        assertThat(updatedInvitation).isNotNull();
        assertThat(updatedInvitation.getStatus()).isEqualTo("DECLINED");
    }

    @Test
    public void testUnlinkSubAccount() {
        orchestrationService.upgradeAccountToBusinessOwner(businessOwner.getAccountId());
        String invitationTokenGenerated = orchestrationService.sendInvitation(businessOwner.getAccountId(),
                subAccount1.getAccountId());
        InvitationDTO invitationDTO = orchestrationService.findByToken(invitationTokenGenerated);
        orchestrationService.respondToInvitation(subAccount1.getAccountId(),
                invitationDTO.getInvitationToken(), true, true);

        String result = orchestrationService.unlinkSubAccount(businessOwner.getAccountId(), subAccount1.getAccountId());
        assertThat(result).isEqualTo("Account unlinked successfully");
    }

    @Test
    public void testGetSubAccountOrderFullHistory() {
        orchestrationService.upgradeAccountToBusinessOwner(businessOwner.getAccountId());
        String invitationTokenGenerated = orchestrationService.sendInvitation(businessOwner.getAccountId(),
                subAccount2.getAccountId());
        InvitationDTO invitationDTO = orchestrationService.findByToken(invitationTokenGenerated);
        orchestrationService.respondToInvitation(subAccount2.getAccountId(),
                invitationDTO.getInvitationToken(), true, true);

        List<OrderDTO> orderHistory = orchestrationService.getSubAccountOrderHistory(businessOwner.getAccountId(),
                subAccount2.getAccountId());
        assertThat(orderHistory).isNotNull();
        assertThat(orderHistory.size()).isEqualTo(7);
    }

    @Test
    public void testGetSubAccountOrderPartialHistory() {
        orchestrationService.upgradeAccountToBusinessOwner(businessOwner.getAccountId());
        String invitationTokenGenerated = orchestrationService.sendInvitation(businessOwner.getAccountId(),
                subAccount2.getAccountId());
        InvitationDTO invitationDTO = orchestrationService.findByToken(invitationTokenGenerated);
        orchestrationService.respondToInvitation(subAccount2.getAccountId(),
                invitationDTO.getInvitationToken(), true, false);

        List<OrderDTO> orderHistory = orchestrationService.getSubAccountOrderHistory(businessOwner.getAccountId(),
                subAccount2.getAccountId());
        assertThat(orderHistory).isNotNull();
        assertThat(orderHistory.size()).isEqualTo(7);
    }
}

