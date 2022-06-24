package com.unvise.bankingsystemapp.account;

import com.unvise.bankingsystemapp.deposit.Deposit;
import com.unvise.bankingsystemapp.credit.Credit;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "credit_history",
            joinColumns = @JoinColumn(name = "account_history_id"),
            inverseJoinColumns = @JoinColumn(name = "credit_id")
    )
    private List<Credit> credits;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "transaction_history",
            joinColumns = @JoinColumn(name = "account_history_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private List<Transaction> transaction;
}
