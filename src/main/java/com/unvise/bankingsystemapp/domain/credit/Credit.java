package com.unvise.bankingsystemapp.domain.credit;

import com.unvise.bankingsystemapp.domain.account.history.AccountHistory;
import com.unvise.bankingsystemapp.domain.audit.DateAudit;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "credit")
@Builder
@Getter
@Setter
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

    @Column(name = "currency", length = 3, nullable = false)
    private CurrencyType currency;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_history_id", nullable = false, foreignKey = @ForeignKey(name = "credit_account_history_fk"))
    @ToString.Exclude
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

    public void topUp(BigDecimal value) {
        addToCurrent(value);
        subtractFromRemain(value);
    }

    public void addToCurrent(BigDecimal value) {
        this.current = this.current.add(value);
    }

    public void subtractFromCurrent(BigDecimal value) {
        this.current = this.current.subtract(value);
    }

    public void addToRemain(BigDecimal value) {
        this.remain = this.remain.add(value);
    }

    public void subtractFromRemain(BigDecimal value) {
        this.remain = this.remain.subtract(value);
    }


}
