package com.vyne.assignment.transactionservice.service;

import com.vyne.assignment.transactionservice.entity.Transaction;
import com.vyne.assignment.transactionservice.error.exception.TransactionNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    /**
     * Return a list of {@link Transaction}
     *
     *
     * @return List<{@link Transaction}>
     */
    List<Transaction> getTransactions();

    /**
     * Return a {@link Transaction}.
     *
     * @return {@link Transaction}
     */
    Optional<Transaction> getTransaction(Long id);

    /**
     * Return a list of {@link Transaction} filtered by status
     *
     *
     * @return List<{@link Transaction}>
     */
    List<Transaction> getTransactionByStatus(String status);

    /**
     * Create a new {@link Transaction}
     *
     *
     * @return The new {@link Transaction}
     */
    Transaction saveTransaction(Transaction newTransaction);

    /**
     * Update an existing {@link Transaction}
     *
     *
     * @return The updated {@link Transaction}
     *
     * @throws TransactionNotFoundException When the transaction doesn't exist
     */
    Transaction updateTransaction(Long id, Transaction updatedTransaction);

    /**
     * Cancel an existing {@link Transaction}
     *
     * @throws TransactionNotFoundException When the transaction doesn't exist
     */
    void cancelTransaction(Long id);

}
