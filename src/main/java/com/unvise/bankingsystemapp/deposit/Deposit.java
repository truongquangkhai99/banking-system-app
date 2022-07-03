package com.unvise.bankingsystemapp.deposit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unvise.bankingsystemapp.account.history.AccountHistory;
import com.unvise.bankingsystemapp.audit.DateAudit;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "deposit")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Deposit extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposit_seq_gen")
    @SequenceGenerator(name = "deposit_seq_gen", sequenceName = "deposit_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "intense_rate", precision = 18, scale = 6, nullable = false)
    private BigDecimal intenseRate;

    @Column(name = "balance", precision = 18, scale = 4, nullable = false)
    private BigDecimal balance;

    @Column(name = "currency", length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "deposit")
    @ToString.Exclude
    private AccountHistory accountHistory;

    @PrePersist
    private void insertNewInstance() {
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
    }

    public void addToBalance(BigDecimal value) {
        this.balance = this.balance.add(value);
    }

    public void subtractFromBalance(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }

}
