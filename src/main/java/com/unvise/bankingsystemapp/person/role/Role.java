package com.unvise.bankingsystemapp.person.role;

import com.unvise.bankingsystemapp.person.role.RoleType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "role", uniqueConstraints = {
        @UniqueConstraint(name = "role_uk", columnNames = {"role"})
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq_gen")
    @SequenceGenerator(name = "role_seq_gen", sequenceName = "role_seq_gen", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Override
    public String getAuthority() {
        return role.getRoleTypeAsString();
    }

}
