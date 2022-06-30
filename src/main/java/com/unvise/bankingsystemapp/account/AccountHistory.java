package com.unvise.bankingsystemapp.account;

import com.unvise.bankingsystemapp.credit.Credit;
import com.unvise.bankingsystemapp.deposit.Deposit;
import com.unvise.bankingsystemapp.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account_history")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_history_seq_gen")
    @SequenceGenerator(name = "account_history_seq_gen", sequenceName = "account_history_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "deposit_id",
            foreignKey = @ForeignKey(name = "account_history_deposit_fk")
    )
    private Deposit deposit;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountHistory")
    private List<Credit> credits;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountHistory")
    private List<Transaction> transaction;

    @PrePersist
    private void insertNewInstance() {
        if (credits == null) {
            credits = List.of();
        }
        if (transaction == null) {
            transaction = List.of();
        }
    }
}
