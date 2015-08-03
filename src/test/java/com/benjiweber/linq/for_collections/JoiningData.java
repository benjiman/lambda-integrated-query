package com.benjiweber.linq.for_collections;

import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.for_collections.DSL.from;
import static com.benjiweber.linq.for_collections.DSL.into;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class JoiningData {
    @Test
    public void cross_join() {
        List<Integer> results =
            from(asList(1, 2, 3))
                .from(asList(10, 20, 30))
                .select(into((a, b) -> a * b))
                .list();

        assertEquals(
                asList(10, 20, 30, 20, 40, 60, 30, 60, 90),
                results
        );
    }

    @Test
    public void inner_join() {
        List<Integer> results =
            from(asList(1, 2, 3))
                .join(asList(10, 20, 30)).on((a, b) -> String.valueOf(b).startsWith(String.valueOf(a)))
                .select(into((a, b) -> a * b))
                .list();

        assertEquals(
                asList(10,40,90),
                results
        );
    }

}
