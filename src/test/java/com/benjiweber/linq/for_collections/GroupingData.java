package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.Group;
import com.benjiweber.linq.tuples.Tuple;
import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.for_collections.DSL.*;
import static com.benjiweber.linq.tuples.Tuple.tuple;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class GroupingData {

    @Test
    public void group() {
        List<Integer> numbers = asList(35, 44, 200, 84, 3987, 4, 199, 329, 446, 208);

        List<Tuple<String, List<Integer>>> result =
            from(numbers)
                .groupBy(number -> number % 2)
                .select(into((key, nos) ->
                    key == 0 ? tuple("Even Numbers", nos) : tuple("Odd Numbers", nos))
                ).list();

        assertEquals(
            asList(
                tuple("Even Numbers", asList(44, 200, 84, 4, 446, 208)),
                tuple("Odd Numbers", asList(35, 3987, 199, 329))
            ),
            result
        );
    }
}
