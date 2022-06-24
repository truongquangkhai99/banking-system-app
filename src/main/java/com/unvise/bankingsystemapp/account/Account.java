package com.unvise.bankingsystemapp.account;

import com.unvise.bankingsystemapp.currency.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_gen")
    @SequenceGenerator(name = "account_seq_gen", sequenceName = "account_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "account_security_details_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "account_account_security_details_fk")
    )
    private AccountSecurityDetails accountSecurityDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "account_history_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "account_account_history_fk")
    )
    private AccountHistory accountHistory;

}
