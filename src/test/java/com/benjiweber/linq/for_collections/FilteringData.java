package com.benjiweber.linq.for_collections;

import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.for_collections.DSL.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class FilteringData {
    @Test
    public void where_predicate() {
        List<String> words = asList("the", "quick", "brown", "fox", "jumps");

        List<String> result =
            from(words)
                .where(word -> word.length() == 3)
                .list();

        assertEquals(
            asList("the","fox"),
            result
        );
    }

    @Test
    public void ofType() {
        List<Object> things = asList("the", 5, "quick", 3, "brown", "fox");

        List<String> result =
            from(things)
                .ofType(String.class)
                .list();

        assertEquals(
            asList("the", "quick", "brown", "fox"),
            result
        );
    }
}
