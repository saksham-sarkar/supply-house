package com.supplyhouse.account.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ACCOUNT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    private LocalDateTime accountLinkDate; // Tracks when subaccount is linked

    // Field to store whether subaccount shares full history
    @Column(nullable = false)
    private boolean shareFullHistory;

    @Column(nullable = false)
    private boolean isBusinessOwner;

    // Many-to-one relationship with the business owner
    @ManyToOne
    @JoinColumn(name = "business_owner_id")
    private Account businessOwnerAccount;

    // One-to-many relationship with subaccounts
    @OneToMany(mappedBy = "businessOwnerAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> subAccounts = new ArrayList<>();
}
