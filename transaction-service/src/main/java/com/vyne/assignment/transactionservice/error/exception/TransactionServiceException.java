package com.vyne.assignment.transactionservice.error.exception;

public class TransactionServiceException extends RuntimeException {
    String error;

    public TransactionServiceException(String message, String error) {
        super(message);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
