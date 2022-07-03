package com.unvise.bankingsystemapp.account.history;

import com.unvise.bankingsystemapp.account.account.Account;
import com.unvise.bankingsystemapp.credit.Credit;
import com.unvise.bankingsystemapp.deposit.Deposit;
import com.unvise.bankingsystemapp.transaction.transaction.Transaction;
import lombok.*;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountHistory", cascade = CascadeType.ALL)
    private List<Transaction> transaction;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "accountHistory")
    @ToString.Exclude
    private Account account;

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
