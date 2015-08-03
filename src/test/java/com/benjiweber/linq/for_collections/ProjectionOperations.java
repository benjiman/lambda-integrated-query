package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.example.domain.flowers.Bouquet;
import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.example.domain.flowers.Bouquet.bouquet;
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

    @Test
    public void select_vs_selectMany() {
        List<Bouquet> bouquets = asList(
            bouquet("sunflower", "daisy", "daffodil", "larkspur"),
            bouquet("tulip", "rose", "orchid"),
            bouquet("gladiolis", "lily", "snapdragon", "aster", "protea"),
            bouquet("larkspur", "lilac", "iris", "dahlia")
        );

        List<List<String>> selected =
            from(bouquets)
                .select(bq -> bq.flowers())
                .list();

        assertEquals(
            asList(
                asList("sunflower", "daisy", "daffodil", "larkspur"),
                asList("tulip", "rose", "orchid"),
                asList("gladiolis", "lily", "snapdragon", "aster", "protea"),
                asList("larkspur", "lilac", "iris", "dahlia")
            ),
            selected
        );

        List<String> selectManyed =
            from(bouquets)
                .selectMany(bq -> bq.flowers())
                .list();

        assertEquals(
            asList(
                "sunflower",
                "daisy",
                "daffodil",
                "larkspur",
                "tulip",
                "rose",
                "orchid",
                "gladiolis",
                "lily",
                "snapdragon",
                "aster",
                "protea",
                "larkspur",
                "lilac",
                "iris",
                "dahlia"
            ),
            selectManyed
        );


    }
}
