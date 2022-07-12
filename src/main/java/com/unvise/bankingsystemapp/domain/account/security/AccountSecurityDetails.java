package com.unvise.bankingsystemapp.domain.account.security;

import com.unvise.bankingsystemapp.domain.account.account.Account;
import com.unvise.bankingsystemapp.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "account_security_details")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccountSecurityDetails extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_sec_details_seq_gen")
    @SequenceGenerator(name = "acc_sec_details_seq_gen", sequenceName = "acc_sec_details_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "accountSecurityDetails")
    @ToString.Exclude
    private Account account;

}
