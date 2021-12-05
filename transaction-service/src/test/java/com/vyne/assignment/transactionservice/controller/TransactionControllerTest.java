package com.vyne.assignment.transactionservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vyne.assignment.transactionservice.api.ApiHelper;
import com.vyne.assignment.transactionservice.entity.Transaction;
import com.vyne.assignment.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

    private static final String TRANSACTIONS_FIND_ENDPOINT = "/transactions/find";
    private static final String TRANSACTIONS_FINDBYID_ENDPOINT = "/transactions/find/1";
    private static final String TRANSACTIONS_FINDBYSTATUS_ENDPOINT = "/transactions/find/?status=authorised";
    private static final String TRANSACTIONS_SAVE_ENDPOINT = "/transactions";
    private static final String TRANSACTIONS_UPDATE_ENDPOINT = "/transactions/1";
    private static final String TRANSACTIONS_DELETE_ENDPOINT = "/transactions/1";

    private static final String BASIC_AUTH = "Basic dXNlcjpwYXNzd29yZA==";

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getTransactions_transactionListIsEmpty()
            throws Exception {
        // Given
        List<Transaction> expectedTransactionList = Collections.emptyList();
        given(transactionService.getTransactions()).willReturn(expectedTransactionList);

        // When
        MockHttpServletResponse response = mockMvc.perform(get(TRANSACTIONS_FIND_ENDPOINT)).andReturn().getResponse();

        // Then
        ObjectMapper mapper = new ObjectMapper();
        List<Transaction> actualTransactionsList = mapper.readValue(response.getContentAsString(), new TypeReference<List<Transaction>>() {
        });

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expectedTransactionList, actualTransactionsList);
    }

    @Test
    public void getTransactions_transactionsListIsRetrieved() throws Exception {
        // Given
        Transaction transaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        List<Transaction> expectedTransactionsList = Arrays.asList(transaction);
        given(transactionService.getTransactions()).willReturn(expectedTransactionsList);

        // When
        MockHttpServletResponse response = mockMvc.perform(get(TRANSACTIONS_FIND_ENDPOINT)).andReturn().getResponse();

        // Then
        ObjectMapper mapper = new ObjectMapper();
        List<Transaction> actualTransactionsList = mapper.readValue(response.getContentAsString(), new TypeReference<List<Transaction>>() { });

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expectedTransactionsList, actualTransactionsList);
    }

    @Test
    public void getTransactionByIdApiVersion1_transactionIsRetrieved() throws Exception {
        // Given
        Transaction expectedTransaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        given(transactionService.getTransaction(anyLong())).willReturn(Optional.of(expectedTransaction));

        // When
        MockHttpServletResponse response = mockMvc.perform(get(TRANSACTIONS_FINDBYID_ENDPOINT)
                .header("Accept", ApiHelper.V1_HEADER)).andReturn().getResponse();

        // Then
        ObjectMapper mapper = new ObjectMapper();
        Transaction actualTransaction = mapper.readValue(response.getContentAsString(), Transaction.class);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expectedTransaction, actualTransaction);
        assertEquals(expectedTransaction.getDescription(), actualTransaction.getDescription());
    }

    @Test
    public void getTransactionByIdApiVersion2_transactionIsRetrieved() throws Exception {
        // Given
        Transaction expectedTransaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        given(transactionService.getTransaction(anyLong())).willReturn(Optional.of(expectedTransaction));

        // When
        MockHttpServletResponse response = mockMvc.perform(get(TRANSACTIONS_FINDBYID_ENDPOINT)
                .header("Accept", ApiHelper.V2_HEADER)).andReturn().getResponse();

        // Then
        ObjectMapper mapper = new ObjectMapper();
        Transaction actualTransaction = mapper.readValue(response.getContentAsString(), Transaction.class);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expectedTransaction, actualTransaction);
        assertEquals(expectedTransaction.getDescription(), actualTransaction.getDescription());
    }

    @Test
    public void getTransactionByStatus_transactionsListIsRetrieved() throws Exception {
        // Given
        Transaction transaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        List<Transaction> expectedTransactionsList = Arrays.asList(transaction);
        given(transactionService.getTransactionByStatus(anyString())).willReturn(expectedTransactionsList);

        // When
        MockHttpServletResponse response = mockMvc.perform(get(TRANSACTIONS_FINDBYSTATUS_ENDPOINT)).andReturn().getResponse();

        // Then
        ObjectMapper mapper = new ObjectMapper();
        List<Transaction> actualTransactionsList = mapper.readValue(response.getContentAsString(), new TypeReference<List<Transaction>>() { });

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expectedTransactionsList, actualTransactionsList);
    }

    @Test
    public void saveTransaction_newTransactionIsRetrieved() throws Exception {
        // Given
        Transaction expectedTransaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        given(transactionService.saveTransaction(any())).willReturn(expectedTransaction);

        ObjectMapper mapper = new ObjectMapper();

        // When
        MockHttpServletResponse response = mockMvc.perform(post(TRANSACTIONS_SAVE_ENDPOINT)
                .header("Authorization", BASIC_AUTH)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(expectedTransaction)))
                .andReturn().getResponse();

        // Then
        Transaction actualTransaction = mapper.readValue(response.getContentAsString(), Transaction.class);

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void updateTransaction_updatedTransactionIsRetrieved() throws Exception {
        // Given
        Transaction expectedTransaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "updated transaction");
        given(transactionService.updateTransaction(anyLong(), any())).willReturn(expectedTransaction);

        ObjectMapper mapper = new ObjectMapper();

        // When
        MockHttpServletResponse response = mockMvc.perform(put(TRANSACTIONS_UPDATE_ENDPOINT)
                        .header("Authorization", BASIC_AUTH)
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(expectedTransaction)))
                .andReturn().getResponse();

        // Then
        Transaction actualTransaction = mapper.readValue(response.getContentAsString(), Transaction.class);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void deleteTransaction_transactionIsDeleted() throws Exception {
        // When
        MockHttpServletResponse response = mockMvc.perform(delete(TRANSACTIONS_DELETE_ENDPOINT)
                        .header("Authorization", BASIC_AUTH))
                .andReturn().getResponse();

        // Then
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}