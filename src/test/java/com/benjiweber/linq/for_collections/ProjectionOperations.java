package com.benjiweber.linq.for_collections;

import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.for_collections.DSL.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ProjectionOperations {
    @Test
    public void select() {
        List<String> words = asList("an", "apple", "a", "day");

        List<String> result =
            from(words)
                .select(word -> word.substring(0, 1))
                .list();

        assertEquals(
                asList("a", "a", "a", "d"),
                result
        );
    }

    @Test
    public void selectMany() {
        List<String> phrases = asList("an apple a day", "the quick brown fox");

        List<String> result =
            from(phrases)
                .from(phrase -> asList(phrase.split(" ")))
                .select(into((phrase, word) -> word))
                .list();

        assertEquals(
            asList("an", "apple", "a", "day", "the", "quick", "brown", "fox"),
            result
        );

    }
}
