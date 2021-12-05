package com.vyne.assignment.transactionservice.service;

import com.vyne.assignment.transactionservice.entity.Transaction;
import com.vyne.assignment.transactionservice.error.exception.TransactionNotFoundException;
import com.vyne.assignment.transactionservice.error.message.ExceptionMessages;
import com.vyne.assignment.transactionservice.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DefaultTransactionService implements TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public DefaultTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getTransactions() {
        return this.transactionRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Transaction> getTransaction(Long id) {
        return transactionRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getTransactionByStatus(String status) {
        return transactionRepository.findByStatus(status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction saveTransaction(Transaction newTransaction) {
        Transaction transaction = this.transactionRepository.save(newTransaction);
        log.info("Created transaction with id={}", transaction.getId());
        return transaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction updateTransaction(Long transactionId, Transaction updatedTransaction) {
        Optional<Transaction> optTransaction = this.transactionRepository.findById(transactionId);
        if(!optTransaction.isPresent()) {
            throw new TransactionNotFoundException(ExceptionMessages.TRANSACTION_NOT_FOUND_EXCEPTION_MESSAGE,
                    ExceptionMessages.TRANSACTION_NOT_FOUND_EXCEPTION_ERROR, transactionId);
        }
        updatedTransaction.setId(transactionId);
        Transaction newTransaction = this.transactionRepository.save(updatedTransaction);
        log.info("Updated transaction with id={}", newTransaction.getId());

        return newTransaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelTransaction(Long transactionId) {
        Optional<Transaction> optTransaction = this.transactionRepository.findById(transactionId);
        if(!optTransaction.isPresent()) {
            throw new TransactionNotFoundException(ExceptionMessages.TRANSACTION_NOT_FOUND_EXCEPTION_MESSAGE,
                    ExceptionMessages.TRANSACTION_NOT_FOUND_EXCEPTION_ERROR, transactionId);
        }
        this.transactionRepository.deleteById(transactionId);
        log.info("Cancelled transaction with id={}", transactionId);
    }
}
