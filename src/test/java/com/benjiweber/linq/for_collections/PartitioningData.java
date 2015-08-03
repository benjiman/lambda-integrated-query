package com.benjiweber.linq.for_collections;

import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.for_collections.DSL.from;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class PartitioningData {

    @Test
    public void skip() {
        List<String> words = asList("an", "apple", "a", "day", "keeps", "the", "doctor", "away");

        List<String> result = from(words)
            .skip(4)
            .list();

        assertEquals(
                asList("keeps","the","doctor","away"),
                result
        );
    }
}
