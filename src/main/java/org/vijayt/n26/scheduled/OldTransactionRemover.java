package org.vijayt.n26.scheduled;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.vijayt.n26.service.TransactionDataHandler;

@Service
@AllArgsConstructor
@Slf4j
public class OldTransactionRemover {
    private final TransactionDataHandler transactionDataHandler;

    @Scheduled(fixedDelay = 1000)
    public void clearOldTransaction() {
        int numberOfTransactionRemoved = transactionDataHandler.clearOldTransactions();
        log.debug("removed {} old transactions", numberOfTransactionRemoved);
    }
}
