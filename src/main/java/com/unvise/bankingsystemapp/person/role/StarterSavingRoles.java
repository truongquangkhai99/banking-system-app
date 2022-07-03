package com.unvise.bankingsystemapp.person.role;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StarterSavingRoles implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.findAll().isEmpty()) {
            var enums = RoleType.values();

            for (RoleType role : enums) {
                roleRepository.save(new Role(null, role));
            }
        }
    }

}
