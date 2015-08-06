package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.example.domain.flowers.CarnivorousPlant;
import com.benjiweber.linq.example.domain.flowers.Plant;
import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.example.domain.flowers.CarnivorousPlant.carnivorousPlant;
import static com.benjiweber.linq.for_collections.DSL.from;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ConvertingDataTypes {

    @Test
    public void ofType() {
        List<Plant> plants = asList(
            carnivorousPlant("Venus Fly Trap", "Snap Trap"),
            carnivorousPlant("Pitcher Plant", "Pitfall Trap" ),
            carnivorousPlant("Sundew", "Flypaper Trap" ),
            carnivorousPlant("Waterwheel Plant", "Snap Trap")
        );

        List<String> result =
            from(plants)
                .ofType(CarnivorousPlant.class)
                .whereProperty(CarnivorousPlant::trapType).equalTo("Snap Trap")
                .select(Plant::name)
                .list();

        assertEquals(
            asList(
                "Venus Fly Trap",
                "Waterwheel Plant"
            ),
            result
        );
    }
}
