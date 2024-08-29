package com.supplyhouse.orchestration.client;

import com.supplyhouse.orchestration.model.dto.InvitationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "invitation-service", url = "${services.invitation-service.url}")
public interface InvitationServiceClient {

    @PostMapping("/invitation/api/send")
    String sendNewInvitation(@RequestBody InvitationDTO dto);

    @PostMapping("/invitation/api/accept")
    String acceptInvitation(@RequestParam String invitationToken);

    @PostMapping("/invitation/api/decline")
    String declineInvitation(@RequestParam String invitationToken);

    @GetMapping("/invitation/api/{invitationToken}")
    InvitationDTO getInvitationByToken(@PathVariable String invitationToken);

    @GetMapping("/invitation/api/find-all")
    List<InvitationDTO> findAll();
}
