package com.unvise.bankingsystemapp.deposit;

import com.unvise.bankingsystemapp.audit.DateAudit;
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

    @Column(name = "intense_rate", nullable = false)
    private BigDecimal intenseRate;

    @Column(name = "balance", precision = 18, scale = 4, nullable = false)
    private BigDecimal balance;

    @PrePersist
    private void insertNewInstance() {
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
    }

}
