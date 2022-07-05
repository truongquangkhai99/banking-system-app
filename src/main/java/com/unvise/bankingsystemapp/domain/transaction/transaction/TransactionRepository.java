package com.unvise.bankingsystemapp.domain.transaction.transaction;

import com.unvise.bankingsystemapp.common.BaseRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Long> {

    @EntityGraph(value = "transaction-details-account-history-graph")
    @Override
    List<Transaction> findAll();

    @EntityGraph(value = "transaction-details-account-history-graph")
    @Override
    Optional<Transaction> findById(Long aLong);

}
