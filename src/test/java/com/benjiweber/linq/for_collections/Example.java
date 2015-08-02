package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.example.domain.SampleCustomers.Customer;
import com.benjiweber.linq.example.domain.SampleCustomers.Order;
import org.junit.Test;

import java.util.*;

import static com.benjiweber.linq.example.domain.SampleCustomers.Order.order;
import static com.benjiweber.linq.example.domain.SampleCustomers.OrderDate.orderDate;
import static com.benjiweber.linq.example.domain.SampleCustomers.getCustomerList;
import static com.benjiweber.linq.for_collections.DSL.*;
import static com.benjiweber.linq.for_collections.Example.Person.person;
import static com.benjiweber.linq.for_collections.Example.Pet.pet;
import static com.benjiweber.linq.tuples.Tuple.tuple;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class Example {
    List<Customer> customerList = getCustomerList();

    @Test
    public void a_company_orders_in_2015() {

        List<Order> ordersIn2015 = from(customerList)
                .where(company -> Objects.equals("A Company", company.companyName()))
                .selectMany(c -> c.orders())
                .where(order -> Objects.equals(2015, order.orderDate().year()))
                .list();

        assertEquals(
                asList(order(orderDate(2015, 4)), order(orderDate(2015, 4)), order(orderDate(2015, 3))),
                ordersIn2015
        );

    }

    @Test
    public void a_company_orders_in_2015_whereEquals() {
        List<Order> ordersIn2015 = from(customerList)
                .whereEquals("A Company", Customer::companyName)
                .selectMany(c -> c.orders())
                .whereEquals(2015, order -> order.orderDate().year())
                .list();

        assertEquals(
                asList(order(orderDate(2015, 4)), order(orderDate(2015, 4)), order(orderDate(2015, 3))),
                ordersIn2015
        );

    }

    // Example from https://code.msdn.microsoft.com/LINQ-to-DataSets-Grouping-c62703ea
    @Test
    public void linq43_example_grouping() {
        from(customerList)
                .select(c -> tuple(
                        c.companyName(),
                        from(c.orders())
                                .groupBy(o -> o.orderDate().year())
                                .select(yg -> tuple(
                                        yg.one(),
                                        from(yg.two())
                                                .groupBy(o -> o.orderDate().month())
                                                .select(mg -> tuple(mg.one(), mg.two()))

                                ))
            ))
            .forEach(System.out::println);
    }

    @Test
    public void anonymous_types_projection() {
        List<String> result =
            from(customerList)
                .select(customer -> tuple(customer.companyName(), customer.companyName().length()))
                .select(into((name, length) -> name + length))
                .list();

        assertEquals(
                asList("A Company9", "Another company15"),
                result
        );
    }

    @Test
    public void anonymous_types_filter() {
        List<String> result = from(customerList)
            .select(customer -> tuple(customer.companyName(), customer.companyName().length()))
            .where(match((name, length) -> name.contains("t")))
            .select(into((name, length) -> name))
            .list();

        assertEquals(
            asList("Another company"),
            result
        );
    }


    @Test
    public void anonymous_types_consumer() {
        from(customerList)
            .select(customer -> tuple(customer.companyName(), customer.companyName().length()))
            .forEach(of((name, length) -> System.out.println(name)));
    }

    @Test
    public void aggregate_function() {
        Long result =
            from(customerList)
                .aggregate(customer -> asLong(customer.companyName().length())).into(sum());

        assertEquals(
                asLong(24),
                result
        );
    }

    @Test
    public void default_sum() {
        Long result =
            from(customerList)
                .sum(customer -> asLong(customer.companyName().length()));

        assertEquals(
                asLong(24),
                result
        );
    }

    @Test
    public void default_max() {
        Long result =
                from(customerList)
                    .max(customer -> asLong(customer.companyName().length()));

        assertEquals(
                asLong(15),
                result
        );
    }

    @Test
    public void default_min() {
        Long result =
                from(customerList)
                        .min(customer -> asLong(customer.companyName().length()));

        assertEquals(
                asLong(9),
                result
        );
    }

    @Test
    public void default_count() {
        Long result =
                from(customerList)
                    .count();

        assertEquals(
                asLong(2),
                result
        );
    }

    @Test
    public void first() {
        String result =
                from(customerList)
                    .select(Customer::companyName)
                     .first().orElse("unknown");

        assertEquals(
            "A Company",
            result
        );
    }

    @Test
    public void last() {
        String result =
                from(customerList)
                        .select(Customer::companyName)
                        .last().orElse("unknown");

        assertEquals(
                "Another company",
                result
        );
    }

    @Test
    public void skip() {
        String result =
            from(customerList)
                .select(Customer::companyName)
                .skip(1)
                .first().orElse("unknown");

        assertEquals(
                "Another company",
                result
        );
    }

    @Test
    public void cross_join() {
        List<Integer> results =
            from(asList(1, 2, 3))
                .from(asList(10, 20, 30))
                .select(into((a, b) -> a * b))
                .list();

        assertEquals(
            asList(10, 20, 30, 20, 40, 60, 30, 60, 90),
            results
        );
    }

    @Test
    public void inner_join() {
        List<Integer> results =
                from(asList(1, 2, 3))
                .join(asList(10, 20, 30)).on((a, b) -> String.valueOf(b).startsWith(String.valueOf(a)))
                .select(into((a, b) -> a * b))
                .list();

        assertEquals(
                asList(10,40,90),
                results
        );
    }

    @Test
    public void whereAggregate() {
        List<Person> people = asList(
            person("Charlotte", pet(4), pet(6)),
            person("Bob", pet(4), pet(16), pet(20)),
            person("Rui", pet(40)),
            person("Arlene", pet(11), pet(12))
        );

        List<String> names =
            from(people)
                .whereAggregate(Person::pets).all(pet -> pet.age() > 10)
                .select(Person::name)
                .list();

        assertEquals(
            asList(
                "Rui",
                "Arlene"
            ),
            names
        );
    }

    interface Pet {
        int age();
        static Pet pet(int age) {
            return () -> age;
        }
    }
    interface Person {
        String name();
        List<Pet> pets();
        static Person person(String name, Pet... pets) {
            return new Person(){
                public String name() { return name; }
                public List<Pet> pets() { return asList(pets); }
            };
        }
    }



}
