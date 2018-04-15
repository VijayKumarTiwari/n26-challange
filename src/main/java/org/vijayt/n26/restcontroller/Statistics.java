package org.vijayt.n26.restcontroller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vijayt.n26.model.Stat;
import org.vijayt.n26.service.TransactionDataHandler;

@RestController
@RequestMapping("/statistics")
@Slf4j
@AllArgsConstructor
public class Statistics {
    private final TransactionDataHandler transactionDataHandler;

    @RequestMapping(method = RequestMethod.GET)
    public Stat getStatsForTransactions() {
        return transactionDataHandler.getTransactionStats();
    }


}
