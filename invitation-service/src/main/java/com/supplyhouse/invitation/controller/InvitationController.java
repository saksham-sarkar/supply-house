package com.supplyhouse.invitation.controller;

import com.supplyhouse.invitation.model.dto.InvitationDTO;
import com.supplyhouse.invitation.model.entity.Invitation;
import com.supplyhouse.invitation.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.supplyhouse.invitation.mapper.InvitationMapper.toInvitation;
import static com.supplyhouse.invitation.mapper.InvitationMapper.toInvitationDTO;

@RestController
@RequestMapping("/invitation/api")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @PostMapping("/send")
    public String sendNewInvitation(@RequestBody InvitationDTO dto) {
        Invitation newInvitation = toInvitation(dto);
        return invitationService.sendNewInvitation(newInvitation);
    }

    @PostMapping("/accept")
    public String acceptInvitation(@RequestParam String invitationToken) {
        return invitationService.acceptInvitation(invitationToken);
    }

    @PostMapping("/decline")
    public String declineInvitation(@RequestParam String invitationToken) {
        return invitationService.declineInvitation(invitationToken);
    }

    @GetMapping("/find-all")
    List<InvitationDTO> findAll() {
        List<InvitationDTO> invitationDTOS = new ArrayList<>();
        List<Invitation> invitations = invitationService.findAll();
        for(Invitation  invitation : invitations) {
            invitationDTOS.add(toInvitationDTO(invitation));
        }
        return invitationDTOS;
    }

    @GetMapping("/{invitationToken}")
    InvitationDTO getInvitationByToken(@PathVariable String invitationToken) {
        Invitation invitation = invitationService.findByToken(invitationToken).orElseThrow();
        return toInvitationDTO(invitation);
    }
}
