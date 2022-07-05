package com.unvise.bankingsystemapp.domain.account.security;

import com.unvise.bankingsystemapp.common.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountSecurityDetailsRepository extends BaseRepository<AccountSecurityDetails, Long> {

    Optional<AccountSecurityDetails> findAccountSecurityDetailsByAccount_Id(Long id);

}
