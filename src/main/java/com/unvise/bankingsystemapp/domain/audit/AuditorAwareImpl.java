package com.unvise.bankingsystemapp.domain.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    // todo: refactor this after add Spring Security
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("unvise");
    }

}