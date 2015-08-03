package com.benjiweber.linq.example.domain.customers;

import java.util.Arrays;
import java.util.List;

import static com.benjiweber.linq.example.domain.customers.SampleCustomers.Customer.customer;
import static com.benjiweber.linq.example.domain.customers.SampleCustomers.Order.order;

public class SampleCustomers {

    public static List<Customer> getCustomerList() {
        return Arrays.asList(
                customer("A Company", Arrays.asList(order(OrderDate.orderDate(2015, 4)), order(OrderDate.orderDate(2015, 4)), order(OrderDate.orderDate(2015, 3)), order(OrderDate.orderDate(2014, 3)))),
                customer("Another company", Arrays.asList(order(OrderDate.orderDate(2015, 4)), order(OrderDate.orderDate(2015, 3)), order(OrderDate.orderDate(2014, 3)), order(OrderDate.orderDate(2014, 3))))
        );
    }

    public static class Customer {
        private final String companyName;
        private final List<Order> orders;

        public Customer(String companyName, List<Order> orders) {
            this.companyName = companyName;
            this.orders = orders;
        }

        public String companyName() {
            return companyName;
        }
        public List<Order> orders() {
            return orders;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "companyName='" + companyName + '\'' +
                    ", orders=" + orders +
                    '}';
        }

        static Customer customer(String companyName, List<Order> orders) {
            return new Customer(companyName, orders);
        }
    }

    public static class Order {

        private final OrderDate orderDate;

        public Order(OrderDate orderDate) {
            this.orderDate = orderDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Order order = (Order) o;

            return !(orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null);

        }

        @Override
        public int hashCode() {
            return orderDate != null ? orderDate.hashCode() : 0;
        }

        public OrderDate orderDate() { return orderDate; }
        public static Order order(OrderDate orderDate) {
            return new Order(orderDate);
        }

        @Override
        public String toString() {
            return "Order at " + orderDate();
        }


    }

    public static class OrderDate {
        private final int year;
        private final int month;

        public OrderDate(int year, int month) {
            this.year = year;
            this.month = month;
        }

        public int year() { return year; }
        public int month() { return month; }

        public static OrderDate orderDate(int year, int month) {
            return new OrderDate(year, month);
        }

        @Override
        public String toString() {
            return "(" + year() + "," + month() + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OrderDate orderDate = (OrderDate) o;

            if (year != orderDate.year) return false;
            return month == orderDate.month;

        }

        @Override
        public int hashCode() {
            int result = year;
            result = 31 * result + month;
            return result;
        }
    }
}
