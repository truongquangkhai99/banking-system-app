package com.unvise.bankingsystemapp.domain.account.account;

import com.unvise.bankingsystemapp.domain.common.BaseRepository;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

    @EntityGraph(value = "account-sec-details-person-graph")
    @Override
    List<Account> findAll();

    @EntityGraph(value = "account-sec-details-person-graph")
    @Override
    Optional<Account> findById(Long aLong);

    @EntityGraph(value = "account-sec-details-person-graph")
    List<Account> findByCurrency(CurrencyType currency);

    @EntityGraph(value = "account-sec-details-person-graph")
    Optional<Account> findByAccountHistory_Id(Long id);

}
