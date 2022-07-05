package com.unvise.bankingsystemapp.domain.account.history;

import com.unvise.bankingsystemapp.domain.account.account.Account;
import com.unvise.bankingsystemapp.domain.credit.Credit;
import com.unvise.bankingsystemapp.domain.deposit.Deposit;
import com.unvise.bankingsystemapp.domain.transaction.transaction.Transaction;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "account_history")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "deposit-transactions-credits-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "deposit"),
                @NamedAttributeNode(value = "credits"),
                @NamedAttributeNode(value = "transaction", subgraph = "transaction-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(name = "transaction-subgraph", attributeNodes = {
                        @NamedAttributeNode(value = "transactionDetails")
                })
        }
)
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
    private Set<Credit> credits;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountHistory", cascade = CascadeType.ALL)
    private Set<Transaction> transaction;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "accountHistory")
    @ToString.Exclude
    private Account account;

    @PrePersist
    private void insertNewInstance() {
        if (credits == null) {
            credits = Set.of();
        }
        if (transaction == null) {
            transaction = Set.of();
        }
    }
}
