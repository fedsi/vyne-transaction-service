package com.vyne.assignment.transactionservice.service;

import com.vyne.assignment.transactionservice.entity.Transaction;
import com.vyne.assignment.transactionservice.error.exception.TransactionNotFoundException;
import com.vyne.assignment.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public class DefaultTransactionServiceTest {

    @InjectMocks
    private DefaultTransactionService service;

    @Mock
    private TransactionRepository repository;

    @Test
    public void getTransactions_transactionListIsEmpty() {
        // Given
        List<Transaction> expectedTransactionsList = Collections.emptyList();
        given(repository.findAll()).willReturn(expectedTransactionsList);

        // When
        List<Transaction> actualTransactionsList = service.getTransactions();

        // Then
        assertEquals(expectedTransactionsList, actualTransactionsList);
    }

    @Test
    public void getTransactions_transactionsListIsRetrieved() throws Exception {
        // Given
        Transaction transaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        List<Transaction> expectedTransactionsList = Arrays.asList(transaction);
        given(repository.findAll()).willReturn(expectedTransactionsList);

        // When
        List<Transaction> actualTransactionsList = service.getTransactions();

        // Then
        assertEquals(expectedTransactionsList, actualTransactionsList);
    }

    @Test
    public void getTransactionById_transactionIsRetrieved() throws Exception {
        // Given
        Transaction expectedTransaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        given(repository.findById(anyLong())).willReturn(Optional.of(expectedTransaction));

        // When
        Optional<Transaction> optionalTransaction = service.getTransaction(1l);
        // Then
        Transaction actualTransaction = optionalTransaction.get();
        assertEquals(expectedTransaction, actualTransaction);
        assertEquals(expectedTransaction.getId(), actualTransaction.getId());
        assertEquals(expectedTransaction.getDate(), actualTransaction.getDate());
        assertEquals(expectedTransaction.getStatus(), actualTransaction.getStatus());
        assertEquals(expectedTransaction.getAmount(), actualTransaction.getAmount());
        assertEquals(expectedTransaction.getCurrency(), actualTransaction.getCurrency());
        assertEquals(expectedTransaction.getDescription(), actualTransaction.getDescription());
    }

    @Test
    public void getTransactionByStatus_transactionsListIsRetrieved() throws Exception {
        // Given
        Transaction transaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        List<Transaction> expectedTransactionsList = Arrays.asList(transaction);
        given(repository.findByStatus(anyString())).willReturn(expectedTransactionsList);

        // When
        List<Transaction> actualTransactionsList = service.getTransactionByStatus("authorised");

        // Then
        assertEquals(expectedTransactionsList, actualTransactionsList);
    }

    @Test
    public void saveTransaction_newTransactionIsRetrieved() throws Exception {
        // Given
        Transaction expectedTransaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        given(repository.save(any())).willReturn(expectedTransaction);

        // When
        Transaction actualTransaction = service.saveTransaction(expectedTransaction);

        // Then
        assertEquals(expectedTransaction, actualTransaction);
        assertEquals(expectedTransaction.getId(), actualTransaction.getId());
        assertEquals(expectedTransaction.getDate(), actualTransaction.getDate());
        assertEquals(expectedTransaction.getStatus(), actualTransaction.getStatus());
        assertEquals(expectedTransaction.getAmount(), actualTransaction.getAmount());
        assertEquals(expectedTransaction.getCurrency(), actualTransaction.getCurrency());
        assertEquals(expectedTransaction.getDescription(), actualTransaction.getDescription());
    }

    @Test
    public void updateTransaction_whenIdDoesntExistExceptionIsThrown() throws Exception {
        // Given
        Transaction expectedTransaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "updated transaction");
        given(repository.findById(1l)).willReturn(Optional.empty());
        given(repository.save(expectedTransaction)).willReturn(expectedTransaction);

        // When - Then
        assertThrows(TransactionNotFoundException.class, () -> {
            service.updateTransaction(1l, expectedTransaction);
        });
    }

    @Test
    public void updateTransaction_updatedTransactionIsRetrieved() {
        // Given
        Transaction expectedTransaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        given(repository.findById(1l)).willReturn(Optional.of(expectedTransaction));
        expectedTransaction.setDescription("updated");
        given(repository.save(expectedTransaction)).willReturn(expectedTransaction);

        // When
        Transaction actualTransaction = service.updateTransaction(1l, expectedTransaction);

        // Then
        assertEquals(expectedTransaction, actualTransaction);
        assertEquals(expectedTransaction.getId(), actualTransaction.getId());
        assertEquals(expectedTransaction.getDate(), actualTransaction.getDate());
        assertEquals(expectedTransaction.getStatus(), actualTransaction.getStatus());
        assertEquals(expectedTransaction.getAmount(), actualTransaction.getAmount());
        assertEquals(expectedTransaction.getCurrency(), actualTransaction.getCurrency());
        assertEquals(expectedTransaction.getDescription(), actualTransaction.getDescription());
    }

    @Test
    public void deleteTransaction_whenIdDoesntExistExceptionIsThrown() {
        // Given
        Transaction expectedTransaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        given(repository.findById(1l)).willReturn(Optional.empty());

        // When - Then
        assertThrows(TransactionNotFoundException.class, () -> {
            service.updateTransaction(1l, expectedTransaction);
        });
    }

    @Test
    public void deleteTransaction_transactionIsDeleted() {
        // Given
        Transaction expectedTransaction = new Transaction(1l, new Date(), "authorised", 100, "GBP", "description");
        given(repository.findById(1l)).willReturn(Optional.of(expectedTransaction));

        // When
        service.cancelTransaction(1l);
    }
}