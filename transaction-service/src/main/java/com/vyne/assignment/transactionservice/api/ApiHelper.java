package com.vyne.assignment.transactionservice.api;

/**
 * Used to map all the endpoint and custom headers
 */
public interface ApiHelper {

    /**
     * Transaction endpoint
     */
    // Paths
    String TRANSACTIONS_BASE_PATH = "/transactions";
    String TRANSACTIONS_UPDATE_PATH = "/{transactionId}";
    String TRANSACTIONS_DELETE_PATH = "/{transactionId}";
    String TRANSACTION_FINDALL = "/find";
    String TRANSACTION_FINDBYID = "/find/{transactionId}";
    String TRANSACTIONS_FINDBYSTATUS = "/find/";

    // Headers
    String V1_HEADER = "application/vyne.transaction.service.v1+json";
    String V2_HEADER = "application/vyne.transaction.service.v2+json";
}
