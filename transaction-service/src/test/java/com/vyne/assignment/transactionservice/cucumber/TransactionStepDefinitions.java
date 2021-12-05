package com.vyne.assignment.transactionservice.cucumber;

import com.vyne.assignment.transactionservice.entity.Transaction;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class TransactionStepDefinitions {

    @Autowired
    private HttpClient client;

    private Transaction transaction;
    private ResponseEntity result;

    private static final String X_CUSTOM_HEADER = "X-Custom-Header";

    @Given("we have a valid Transaction object")
    public void weHaveAValidTransactionObject() {
        transaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
    }

    @When("the client post the transaction object")
    public void theClientPostTheTransactionObjectTransactions() {
        result = client.post(transaction);
    }

    @When("the client request a transaction by id using API version {int}")
    public void theClientRequestATransactionUsingAPIVersionApiVersion(int apiVersion) {
        result = client.getById(apiVersion);
    }

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int status) {
        assertThat(result.getStatusCode().value(), is(status));
    }

    @Then("the response transaction field are {string}, {string}, {long}, {string}, {string}")
    public void theResponseTransactionFieldAreAmount(String date, String status, long amount, String currency, String description) {
        Transaction transaction = (Transaction) result.getBody();
        DateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        assertThat(formatter.format(transaction.getDate()), is(date));
        assertThat(transaction.getStatus(), is(status));
        assertThat(transaction.getAmount(), is(amount));
        assertThat(transaction.getCurrency(), is(currency));
        assertThat(transaction.getDescription(), is(description));
    }

    @Then("the response contains the custom header")
    public void theResponseHasTheCustomHeader() {
        HttpHeaders headers = result.getHeaders();
        assertThat(headers.containsKey(X_CUSTOM_HEADER), is(true));
    }

    @Then("the response doesn't contains the custom header")
    public void theResponseDoesnTContainsTheCustomHeader() {
        HttpHeaders headers = result.getHeaders();
        assertThat(headers.containsKey(X_CUSTOM_HEADER), is(false));
    }
}
