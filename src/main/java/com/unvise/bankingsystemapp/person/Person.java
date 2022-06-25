package com.unvise.bankingsystemapp.person;

import com.unvise.bankingsystemapp.account.Account;
import com.unvise.bankingsystemapp.audit.DateAudit;
import com.unvise.bankingsystemapp.role.Role;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "person", uniqueConstraints = {
        @UniqueConstraint(name = "email_uk", columnNames = "email"),
        @UniqueConstraint(name = "phone_uk", columnNames = "phone")
})
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Person extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq_gen")
    @SequenceGenerator(name = "person_seq_gen", sequenceName = "person_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", length = 70, nullable = false)
    private String firstname;

    @Column(name = "last_name", length = 70, nullable = false)
    private String lastname;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "phone", length = 30, nullable = false)
    private String phone;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true
    )
    @JoinColumn(
            name = "account_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "person_account_fk")
    )
    private Account account;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(name = "person_role_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

}
