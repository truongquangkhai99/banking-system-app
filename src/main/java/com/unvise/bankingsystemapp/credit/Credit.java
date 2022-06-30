package com.unvise.bankingsystemapp.credit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unvise.bankingsystemapp.account.AccountHistory;
import com.unvise.bankingsystemapp.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "credit")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Credit extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_seq_gen")
    @SequenceGenerator(name = "credit_seq_gen", sequenceName = "credit_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "total", nullable = false, precision = 18, scale = 4)
    private BigDecimal total;

    @Column(name = "current", nullable = false, precision = 18, scale = 4)
    private BigDecimal current;

    @Column(name = "remain", nullable = false, precision = 18, scale = 4)
    private BigDecimal remain;

    @Column(name = "date_between_payments_in_days", nullable = false)
    private Integer dateBetweenPaymentsInDays;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_history_id", nullable = false, foreignKey = @ForeignKey(name = "credit_account_history_fk"))
    @JsonIgnore
    private AccountHistory accountHistory;

    @PrePersist
    private void init() {
        if (current == null) {
            current = BigDecimal.ZERO;
        }

        if (remain == null) {
            remain = total;
        }

        if (isClosed == null) {
            isClosed = false;
        }
    }

}
