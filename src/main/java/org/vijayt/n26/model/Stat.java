package org.vijayt.n26.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
public class Stat {
    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;
}
