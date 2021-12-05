package com.vyne.assignment.transactionservice.repository;

import com.vyne.assignment.transactionservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t from Transaction t where t.status = :status")
    List<Transaction> findByStatus(@Param("status") String status);
}
