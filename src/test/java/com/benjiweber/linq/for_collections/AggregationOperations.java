package com.benjiweber.linq.for_collections;

import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.for_collections.DSL.from;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class AggregationOperations {

    List<Double> temperatures = asList(72.0, 81.5, 69.3, 88.6, 80.0, 68.5);

    @Test
    public void count() {
        Long count = from(temperatures)
            .count();

        assertEquals(Long.valueOf(6), count);
    }

    @Test
    public void max() {
        Double max = from(temperatures)
                .maxDouble(i -> i);

        assertEquals(Double.valueOf(88.6), max);
    }

    @Test
    public void min() {
        Double min = from(temperatures)
                .minDouble(i -> i);

        assertEquals(Double.valueOf(68.5), min);
    }


    @Test
    public void sum() {
        List<Double> expenses = asList(560.0, 300.0, 1080.5, 29.95, 64.75, 200.0);

        Double sum = from(expenses)
                .sumDouble(i -> i);

        assertEquals(Double.valueOf(2235.2), sum);
    }

    @Test
    public void sumInt() {
        List<Integer> ints = asList(1,2,3,4);

        Long total = from(ints)
                .sum(Long::valueOf);

        assertEquals(Long.valueOf(10), total);
    }
}
