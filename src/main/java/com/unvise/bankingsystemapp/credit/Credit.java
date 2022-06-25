package com.unvise.bankingsystemapp.credit;

import com.unvise.bankingsystemapp.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Column(name = "date_between_payments", nullable = false)
    private LocalDate dateBetweenPayments;

    @Column(name = "is_closed")
    private Boolean isClosed;

}
