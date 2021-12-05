package com.vyne.assignment.transactionservice.error.exception;

/**
 * This exception will be thrown when a transaction with the specified id
 * does not exist.
 */
public class TransactionNotFoundException extends TransactionServiceException {

    Long transactionId;

    public TransactionNotFoundException(String message, String error, Long transactionId) {
        super(message, error);
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}