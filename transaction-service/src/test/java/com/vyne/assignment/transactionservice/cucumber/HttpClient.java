package com.vyne.assignment.transactionservice.cucumber;

import com.vyne.assignment.transactionservice.api.ApiHelper;
import com.vyne.assignment.transactionservice.entity.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class HttpClient {

    private static final String TRANSACTIONS_FINDBYID_ENDPOINT = "http://localhost:8080/api/transactions/find/1";
    private static final String TRANSACTIONS_SAVE_ENDPOINT = "http://localhost:8080/api/transactions";

    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<Transaction> post(final Transaction something) {
        HttpHeaders headers = new HttpHeaders();
        setBasicAuth(headers);
        HttpEntity<Transaction> entity = new HttpEntity<Transaction>(something, headers);

        return  restTemplate.exchange(TRANSACTIONS_SAVE_ENDPOINT, HttpMethod.POST, entity, Transaction.class);
    }

    public ResponseEntity<Transaction> getById(int apiVersion) {
        HttpHeaders headers = new HttpHeaders();
        if (apiVersion == 1 ) {
            setApiVersion1(headers);
        } else if (apiVersion == 2) {
            setApiVersion2(headers);
        }
        HttpEntity<Transaction> entity = new HttpEntity<Transaction>(null, headers);
        return  restTemplate.exchange(TRANSACTIONS_FINDBYID_ENDPOINT, HttpMethod.GET, entity, Transaction.class);
    }

    private void setBasicAuth(HttpHeaders headers) {
        headers.setBasicAuth(USER,PASSWORD);
    }

    private void setApiVersion1(HttpHeaders headers) {
        headers.add(HttpHeaders.ACCEPT, ApiHelper.V1_HEADER);
    }

    private void setApiVersion2(HttpHeaders headers) {
        headers.add(HttpHeaders.ACCEPT, ApiHelper.V2_HEADER);
    }
}
