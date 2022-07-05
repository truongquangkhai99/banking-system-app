package com.unvise.bankingsystemapp.domain.person.role;

import com.unvise.bankingsystemapp.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {

    @Query("SELECT r FROM Role r")
    Set<Role> findAllRolesAsSet();

    Optional<Role> findByRole(RoleType roleType);

}
