package com.unvise.bankingsystemapp.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq_gen")
    @SequenceGenerator(name = "role_seq_gen", sequenceName = "role_seq_gen", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

}
