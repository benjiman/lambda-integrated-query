# lambda-integrated-query
LINQ style queries for Java 8 Streams

```java

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

    @Test
    // Example from https://code.msdn.microsoft.com/LINQ-to-DataSets-Grouping-c62703ea
    @Test
    public void linq43_example_grouping() {
        from(customerList)
            .select(c -> tuple(
                c.companyName(),
                from(c.orders())
                    .groupBy(o -> o.orderDate().year())
                    .select(into((year, orders) -> tuple(
                        year,
                        from(orders)
                            .groupBy(o -> o.orderDate().month())
                            .select(into((month, monthOrders) -> tuple(month, monthOrders)))
                    )))
            )).forEach(System.out::println);
    }
```
