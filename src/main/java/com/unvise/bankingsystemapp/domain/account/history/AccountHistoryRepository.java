package com.unvise.bankingsystemapp.domain.account.history;

import com.unvise.bankingsystemapp.common.BaseRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountHistoryRepository extends BaseRepository<AccountHistory, Long> {

    @EntityGraph(value = "deposit-transactions-credits-entity-graph")
    @Override
    List<AccountHistory> findAll();

    @EntityGraph(value = "deposit-transactions-credits-entity-graph")
    @Override
    Optional<AccountHistory> findById(Long aLong);

}
