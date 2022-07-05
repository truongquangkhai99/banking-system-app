package com.unvise.bankingsystemapp.domain.transaction.transaction;

import com.unvise.bankingsystemapp.domain.account.history.AccountHistory;
import com.unvise.bankingsystemapp.domain.transaction.details.TransactionDetails;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "transaction-details-account-history-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "transactionDetails"),
                @NamedAttributeNode(value = "accountHistory")
        }
)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq_gen")
    @SequenceGenerator(name = "transaction_seq_gen", sequenceName = "transaction_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data", nullable = false)
    private LocalDate date;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "transaction_details_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "transaction_transaction_details_fk")
    )
    private TransactionDetails transactionDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_history_id", foreignKey = @ForeignKey(name = "transaction_account_history_fk"))
    private AccountHistory accountHistory;

}
