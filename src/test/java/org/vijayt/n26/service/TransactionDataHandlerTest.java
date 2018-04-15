package org.vijayt.n26.service;

import org.junit.Before;
import org.junit.Test;
import org.vijayt.n26.config.ApplicationProperties;
import org.vijayt.n26.model.Stat;
import org.vijayt.n26.model.Transaction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TransactionDataHandlerTest {
    private TransactionDataHandler transactionDataHandler;

    @Before
    public void setUp() {
        transactionDataHandler = new TransactionDataHandler(new ApplicationProperties());
    }

    @Test
    public final void noTransactionsResultInZeroValuedStats() {
        Stat stat = transactionDataHandler.getTransactionStats();
        assertThat(stat.getSum(), is(0.0));
        assertThat(stat.getAvg(), is(0.0));
        assertThat(stat.getMax(), is(Double.NEGATIVE_INFINITY));
        assertThat(stat.getMin(), is(Double.POSITIVE_INFINITY));
        assertThat(stat.getCount(), is(0L));
    }

    @Test
    public final void transactionsLessThanConfiguredTimeAreIgnored() {
        addTransactionBefore60Sec();
        addTransactionBefore60Sec();
        addTransactionBefore60Sec();
        transactionDataHandler.clearOldTransactions();
        Stat stat = transactionDataHandler.getTransactionStats();
        assertThat(stat.getSum(), is(0.0));
        assertThat(stat.getAvg(), is(0.0));
        assertThat(stat.getMax(), is(Double.NEGATIVE_INFINITY));
        assertThat(stat.getMin(), is(Double.POSITIVE_INFINITY));
        assertThat(stat.getCount(), is(0L));
    }

    @Test
    public final void transactionsWithinConfiguredTimeAreIncludedInStatsCalculation() {
        addTransactionWithin60Sec();
        addTransactionWithin60Sec();
        addTransactionWithin60Sec();
        transactionDataHandler.clearOldTransactions();
        Stat stat = transactionDataHandler.getTransactionStats();
        assertThat(stat.getSum(), is(3.0));
        assertThat(stat.getAvg(), is(1.0));
        assertThat(stat.getMax(), is(1.0));
        assertThat(stat.getMin(), is(1.0));
        assertThat(stat.getCount(), is(3L));
    }

    @Test
    public final void mixedTransactionsWithinAndOutOfLimitAreHandled() {
        addTransactionWithin60Sec();
        addTransactionWithin60Sec();
        addTransactionBefore60Sec();
        addTransactionBefore60Sec();
        transactionDataHandler.clearOldTransactions();
        Stat stat = transactionDataHandler.getTransactionStats();
        assertThat(stat.getSum(), is(2.0));
        assertThat(stat.getAvg(), is(1.0));
        assertThat(stat.getMax(), is(1.0));
        assertThat(stat.getMin(), is(1.0));
        assertThat(stat.getCount(), is(2L));
    }

    private void addTransactionWithin60Sec() {
        transactionDataHandler.addTransaction(Transaction.builder()
                .amount(1D)
                .timestamp(System.currentTimeMillis())
                .build());
    }

    private void addTransactionBefore60Sec() {
        transactionDataHandler.addTransaction(Transaction.builder()
                .amount(1D)
                .timestamp(System.currentTimeMillis() - 61000)
                .build());
    }
}