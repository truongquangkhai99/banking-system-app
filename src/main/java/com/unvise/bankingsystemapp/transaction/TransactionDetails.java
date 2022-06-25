package com.unvise.bankingsystemapp.transaction;

import com.unvise.bankingsystemapp.account.Account;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction_details")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_details_seq_gen")
    @SequenceGenerator(name = "transaction_details_seq_gen", sequenceName = "transaction_details_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @OneToOne(mappedBy = "transactionDetails")
    private Transaction transaction;

}
