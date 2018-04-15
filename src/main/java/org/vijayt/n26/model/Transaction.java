package org.vijayt.n26.model;

import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    Double amount;
    Long timestamp;
}
