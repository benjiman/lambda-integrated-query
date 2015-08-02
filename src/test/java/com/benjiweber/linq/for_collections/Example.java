package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.example.domain.SampleCustomers;
import com.benjiweber.linq.example.domain.SampleCustomers.Customer;
import com.benjiweber.linq.example.domain.SampleCustomers.Order;
import com.benjiweber.linq.tuples.Tuple;
import org.junit.Test;

import java.util.*;

import static com.benjiweber.linq.example.domain.SampleCustomers.Order.order;
import static com.benjiweber.linq.example.domain.SampleCustomers.OrderDate.orderDate;
import static com.benjiweber.linq.example.domain.SampleCustomers.getCustomerList;
import static com.benjiweber.linq.for_collections.DSL.*;
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
}
