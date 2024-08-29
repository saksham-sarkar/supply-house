package com.supplyhouse.notification.service;

import com.supplyhouse.notification.model.entity.Notification;
import com.supplyhouse.notification.model.enums.NotificationType;
import com.supplyhouse.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    private NotificationRepository notificationRepository;

    public String notifyRecipient(Notification notification, String token) {
        String emailContent = "You have been invited to join " + notification.getSenderId() + ".\n" +
                "Click here to accept: " + generateAcceptInvitationLink(token) + " or decline: " +
                generateDeclineInvitationLink(token);

//        Email email = new Email(recipientMail, "Invitation to Join Business", emailContent);
//        emailSender.send(email);
//        "Email Sent successfully";
        notification.setEmailContent(emailContent);
        notification.setType(NotificationType.EMAIL);
        notificationRepository.save(notification);
        return "Notification sent successfully by " + NotificationType.EMAIL;
    }

    private String generateDeclineInvitationLink(String token) {
        return String.format("http://%s/invitation/decline?token=%s", domainName, token);
    }

    private String generateAcceptInvitationLink(String token) {
        return String.format("http://%s/invitation/accept?token=%s", domainName, token);
    }
}
