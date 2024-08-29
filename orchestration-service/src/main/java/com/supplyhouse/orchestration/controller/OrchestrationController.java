package com.supplyhouse.orchestration.controller;

import com.supplyhouse.orchestration.model.dto.AccountDTO;
import com.supplyhouse.orchestration.model.dto.LinkSubAccountDTO;
import com.supplyhouse.orchestration.model.dto.OrderDTO;
import com.supplyhouse.orchestration.model.dto.RespondToInvitationDTO;
import com.supplyhouse.orchestration.service.OrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orchestration/api")
public class OrchestrationController {

    @Autowired
    private OrchestrationService orchestrationService;

    @PostMapping("/upgrade-account")
    public String upgradeAccount(@RequestParam String accountId) {
        return orchestrationService.upgradeAccountToBusinessOwner(accountId);
    }

    @PostMapping("/create-account")
    public String createAccount(@RequestBody AccountDTO dto) {
        dto = orchestrationService.saveAccount(dto);
        return "Account created with id = " + dto.getAccountId();
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestBody OrderDTO dto) {
        orchestrationService.saveOrder(dto);
        return "Order created with id = " + dto.getOrderId();
    }

    @PostMapping("/send-invitation")
    public String sendInvitation(@RequestParam String businessOwnerId,  @RequestParam String subAccountId) {
        return "Invitation created with token = " + orchestrationService.sendInvitation(businessOwnerId, subAccountId);
    }

    @PostMapping("/respond-to-invitation")
    public String respondToInvitation(@RequestBody RespondToInvitationDTO dto) {
        return orchestrationService.respondToInvitation(
                dto.getSubAccountId(),
                dto.getInvitationToken(),
                dto.isAccept(),
                dto.isShareFullHistory());
    }

    @PostMapping("/get-order-history")
    public List<OrderDTO> getSubAccountOrderHistory(@RequestBody LinkSubAccountDTO dto) {
        return orchestrationService.getSubAccountOrderHistory(
                dto.getBusinessOwnerId(),
                dto.getSubAccountId()
                );
    }

    @PostMapping("/unlink")
    public String unlink(@RequestBody LinkSubAccountDTO dto) {
        return orchestrationService.unlinkSubAccount(
                dto.getBusinessOwnerId(),
                dto.getSubAccountId()
        );
    }
}

