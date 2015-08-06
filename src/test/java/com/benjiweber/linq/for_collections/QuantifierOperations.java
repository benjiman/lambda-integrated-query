package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.example.domain.pets.Person;
import com.benjiweber.linq.example.domain.pets.Pet;
import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.example.domain.pets.Person.person;
import static com.benjiweber.linq.example.domain.pets.Pet.pet;
import static com.benjiweber.linq.for_collections.DSL.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class QuantifierOperations {

    Pet barley = pet("Barley", 4);
    Pet boots = pet("Boots", 1);
    Pet whiskers = pet("Whiskers", 6);
    Pet bluemoon = pet("Blue Moon", 9);
    Pet daisy = pet("Daisy", 3);

    Person charlotte = person("Charlotte", barley, boots);
    Person arlene = person("Arlene", whiskers);
    Person rui= person("Rui", bluemoon, daisy);

    List<Person> people  = asList(charlotte, arlene, rui);


    @Test
    public void all() {
        List<String> result =
            from(people)
                .where(collection(Person::pets)).all(pet -> pet.age() > 2)
                .select(pers -> pers.name())
                .list();

        assertEquals(
            asList("Arlene", "Rui"),
            result
        );
    }

    @Test
    public void any() {
        List<String> result =
            from(people)
                .where(collection(Person::pets)).any(pet -> pet.age() > 6)
                .select(pers -> pers.name())
                .list();

        assertEquals(
                asList("Rui"),
                result
        );
    }


    @Test
    public void contains() {
        List<String> result =
            from(people)
                .where(collection(Person::pets)).contains(boots)
                .select(pers -> pers.name())
                .list();

        assertEquals(
                asList("Charlotte"),
                result
        );
    }
}
