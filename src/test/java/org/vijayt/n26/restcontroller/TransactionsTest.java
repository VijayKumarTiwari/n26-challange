package org.vijayt.n26.restcontroller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.vijayt.n26.config.ApplicationProperties;
import org.vijayt.n26.exception.OldTransactionException;
import org.vijayt.n26.model.Transaction;
import org.vijayt.n26.service.TransactionDataHandler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionsTest {
    @Mock
    private TransactionDataHandler transactionDataHandler;
    @Mock
    private ApplicationProperties properties;

    @InjectMocks
    private Transactions transactions;

    @Test(expected = OldTransactionException.class)
    public final void oldTransactionLeadToException() {
        transactions.recordTransaction(oldTransaction());
    }

    @Test
    public final void transactionWithinAllowedTimeIsProcessed() {
        Transaction transaction = transactionWithinAllowedTime();
        transactions.recordTransaction(transaction);
        verify(properties, times(1)).getSecondsToUseForStats();
        verify(transactionDataHandler, times(1)).addTransaction(transaction);
    }

    private Transaction transactionWithinAllowedTime() {
        return Transaction.builder()
                .amount(1D)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    private Transaction oldTransaction() {
        return Transaction.builder()
                .amount(1D)
                .timestamp(1L)
                .build();
    }
}