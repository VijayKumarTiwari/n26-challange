package org.vijayt.n26.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class Stat {
    private Double sum;
    private Double avg;
    private Double max;
    private Double min;
    private Long count;
}
