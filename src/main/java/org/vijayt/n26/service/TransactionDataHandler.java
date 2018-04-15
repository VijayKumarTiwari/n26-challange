package org.vijayt.n26.service;

import org.springframework.stereotype.Service;
import org.vijayt.n26.config.ApplicationProperties;
import org.vijayt.n26.model.Stat;
import org.vijayt.n26.model.Transaction;

import java.util.DoubleSummaryStatistics;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionDataHandler {
    private final ApplicationProperties properties;
    private CopyOnWriteArrayList<Transaction> transactions;

    public TransactionDataHandler(ApplicationProperties properties) {
        this.properties = properties;
        transactions = new CopyOnWriteArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Stat getTransactionStats() {
        removeOldTransactions(System.currentTimeMillis() - properties.getSecondsToUseForStats() * 1000);
        DoubleSummaryStatistics transactionStats = transactions.stream()
                .map(Transaction::getAmount)
                .collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);

        return convertToStat(transactionStats);
    }

    private void removeOldTransactions(long minValidTimeInMillis) {
        transactions.removeIf(transaction -> transaction.getTimestamp() < minValidTimeInMillis);
    }

    private Stat convertToStat(DoubleSummaryStatistics transactionStats) {
        return Stat.builder()
                .sum(transactionStats.getSum())
                .avg(transactionStats.getAverage())
                .max(transactionStats.getMax())
                .min(transactionStats.getMin())
                .count(transactionStats.getCount())
                .build();
    }
}
