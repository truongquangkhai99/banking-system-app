package com.unvise.bankingsystemapp.account;

import com.unvise.bankingsystemapp.audit.Audit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "account_security_details")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSecurityDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_sec_details_seq_gen")
    @SequenceGenerator(name = "acc_sec_details_seq_gen", sequenceName = "acc_sec_details_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "password_hash", nullable = false)
    private String password_hash;

    @Embedded
    private Audit audit = new Audit();

}
