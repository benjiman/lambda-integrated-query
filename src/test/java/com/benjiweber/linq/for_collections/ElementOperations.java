package com.benjiweber.linq.for_collections;

import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.for_collections.DSL.from;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ElementOperations {

    @Test
    public void first() {
        List<String> words = asList("the", "quick", "brown", "fox");


        String first = from(words)
                .first().orElse("shrug");

        assertEquals("the", first);
    }

    @Test
    public void last() {
        List<String> words = asList("the", "quick", "brown", "fox");


        String last = from(words)
                .last().orElse("shrug");

        assertEquals("fox", last);
    }
}
