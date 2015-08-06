package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.example.domain.pets.Person;
import com.benjiweber.linq.example.domain.pets.Pet;
import org.junit.Test;

import java.util.List;

import static com.benjiweber.linq.example.domain.customers.SampleCustomers.getCustomerList;
import static com.benjiweber.linq.example.domain.pets.Person.person;
import static com.benjiweber.linq.example.domain.pets.Pet.pet;
import static com.benjiweber.linq.for_collections.DSL.*;
import static com.benjiweber.linq.tuples.Tuple.tuple;
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
    public void whereProperty_greaterThan() {
        List<String> result =
            from(people)
                .selectMany(Person::pets)
                .where(property(Pet::age).greaterThan(6))
                .select(Pet::name)
                .list();

        assertEquals(asList("Blue Moon"), result);
    }

    @Test
    public void whereProperty_lessThan() {
        List<String> result =
            from(people)
                .selectMany(Person::pets)
                .where(property(Pet::age).lessThan(2))
                .select(Pet::name)
                .list();

        assertEquals(asList("Boots"), result);
    }

    @Test
    public void whereProperty_equalTo() {
        List<String> result =
            from(people)
                .selectMany(Person::pets)
                .where(property(Pet::age).equalTo(6))
                .select(Pet::name)
                .list();

        assertEquals(asList("Whiskers"), result);
    }

    @Test
    public void anonymous_types_filter() {
        List<String> result = from(getCustomerList())
                .select(customer -> tuple(customer.companyName(), customer.companyName().length()))
                .where(match((name, length) -> name.contains("t")))
                .select(into((name, length) -> name))
                .list();

        assertEquals(
                asList("Another company"),
                result
        );
    }
}
