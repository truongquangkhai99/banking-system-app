package com.unvise.bankingsystemapp.credit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "credit")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_seq_gen")
    @SequenceGenerator(name = "credit_seq_gen", sequenceName = "credit_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

}
