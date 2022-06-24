package com.unvise.bankingsystemapp.deposit;

import com.unvise.bankingsystemapp.audit.Audit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "deposit")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposit_seq_gen")
    @SequenceGenerator(name = "deposit_seq_gen", sequenceName = "deposit_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "intense_rate", nullable = false)
    private BigDecimal intenseRate;

    @Embedded
    private Audit audit = new Audit();

}
