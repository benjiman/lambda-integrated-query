package com.benjiweber.linq.for_collections;

import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.for_collections.DSL.from;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class SortingData {
    @Test
    public void sort() {
        List<String> words = asList("the", "quick", "brown", "fox", "jumps");
        List<String> result =
            from(words)
                .orderBy(String::length)
                .list();

        assertEquals(
            asList("the","fox","quick","brown","jumps"),
            result
        );
    }
}
