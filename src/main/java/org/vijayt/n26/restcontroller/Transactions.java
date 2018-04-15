package org.vijayt.n26.restcontroller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.vijayt.n26.config.ApplicationProperties;
import org.vijayt.n26.exception.OldTransactionException;
import org.vijayt.n26.model.Transaction;
import org.vijayt.n26.service.TransactionDataHandler;

@RestController
@RequestMapping("/transactions")
@Slf4j
@AllArgsConstructor
public class Transactions {
    private final TransactionDataHandler transactionDataHandler;
    private final ApplicationProperties properties;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void recordTransaction(@RequestBody Transaction transaction) {
        if (transaction.getTimestamp() < getMinAllowedTime()) {
            log.warn("{} is older than {}sec the record will not be processed", transaction, properties.getSecondsToUseForStats());
            throw new OldTransactionException();
        } else {
            transactionDataHandler.addTransaction(transaction);
        }
    }

    private Long getMinAllowedTime() {
        return System.currentTimeMillis() - properties.getSecondsToUseForStats() * 1000;
    }
}
