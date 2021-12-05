package com.vyne.assignment.transactionservice.controller;

import com.vyne.assignment.transactionservice.api.ApiHelper;
import com.vyne.assignment.transactionservice.entity.Transaction;
import com.vyne.assignment.transactionservice.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = ApiHelper.TRANSACTIONS_BASE_PATH)
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Return a list of {@link Transaction}
     *
     * @return A list with all existing transaction
     */
    @GetMapping(value = ApiHelper.TRANSACTION_FINDALL)
    public List<Transaction> getTransactions() {
        log.info("Received request to retrieve all transactions");
        return transactionService.getTransactions();
    }

    /**
     * Return a {@link Transaction} using the API version 1
     *
     * @return Return a {@link Transaction} object
     */
    @GetMapping(value = ApiHelper.TRANSACTION_FINDBYID,
            produces = ApiHelper.V1_HEADER)
    public ResponseEntity<Transaction> getByIdV1(@PathVariable Long transactionId) {
        log.info("Received request using API version 1 to retrieve transaction with id={}", transactionId);

        Optional<Transaction> optTransaction = transactionService.getTransaction(transactionId);
        if(optTransaction.isPresent()) {
            String newDescription = addApiVersionToDescription(optTransaction.get(), 1);
            optTransaction.get().setDescription(newDescription);
        }
        return new ResponseEntity(optTransaction, HttpStatus.OK);
    }

    /**
     * Return a {@link Transaction} using the API version 2
     *
     * @return Return a {@link Transaction} object
     */
    @GetMapping(value = ApiHelper.TRANSACTION_FINDBYID,
            produces = ApiHelper.V2_HEADER)
    public ResponseEntity getByIdV2(@PathVariable Long transactionId) {
        log.info("Received request using API version 2 to retrieve transaction with id={}", transactionId);

        Optional<Transaction> optTransaction = transactionService.getTransaction(transactionId);
        if(optTransaction.isPresent()) {
            String newDescription = addApiVersionToDescription(optTransaction.get(), 2);
            optTransaction.get().setDescription(newDescription);
        }
        return new ResponseEntity(optTransaction, HttpStatus.OK);
    }

    /**
     * Return a list of {@link Transaction} filtered by status
     *
     * @return A list with all existing transactions filtered by status
     */
    @GetMapping(value = ApiHelper.TRANSACTIONS_FINDBYSTATUS)
    public List<Transaction> getByStatus(@RequestParam(name = "status", required = true) String status) {
        log.info("Received request to retrieve all transaction by status={}", status);

        return transactionService.getTransactionByStatus(status);
    }

    /**
     * Create a new {@link Transaction}
     *
     * @return The created {@link Transaction} object
     */
    @PostMapping
    ResponseEntity saveTransaction(@Valid @RequestBody Transaction newTransaction) {
        log.info("Received request to create a new transaction");

        Transaction transaction = transactionService.saveTransaction(newTransaction);
        return new ResponseEntity(transaction, HttpStatus.CREATED);
    }

    /**
     * Update an existing {@link Transaction}
     *
     * @return The updated transaction
     */
    @PutMapping(ApiHelper.TRANSACTIONS_UPDATE_PATH)
    ResponseEntity updateTransaction(@PathVariable Long transactionId, @Valid @RequestBody Transaction updatedTransaction) {
        log.info("Received request to update an existing transaction with id={}", transactionId);

        Transaction transaction = transactionService.updateTransaction(transactionId, updatedTransaction);
        return new ResponseEntity(transaction, HttpStatus.OK);
    }

    /**
     * Cancel an existing transaction
     */
    @DeleteMapping(ApiHelper.TRANSACTIONS_DELETE_PATH)
    ResponseEntity deleteTransaction(@PathVariable Long transactionId) {
        log.info("Received request to delete the transaction with id={}", transactionId);

        this.transactionService.cancelTransaction(transactionId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private String addApiVersionToDescription(Transaction transaction, int apiVersion) {
        StringBuilder newDescription = new StringBuilder();
        newDescription.append(transaction.getDescription() == null ? "" : transaction.getDescription());
        newDescription.append("_from API Version");
        newDescription.append(apiVersion);
        return newDescription.toString();
    }
}
