# lambda-integrated-query
LINQ style queries for Java 8 Streams

```java

    @Test
    public void a_company_orders_in_2015() {
        List<Order> ordersIn2015 = from(customerList)
                .where(property(Customer::companyName).equalTo("A Company"))
                .selectMany(Customer::orders)
                .where(order -> order.orderDate().year() == 2015)
                .list();

        assertEquals(
                asList(order(orderDate(2015, 4)), order(orderDate(2015, 4)), order(orderDate(2015, 3))),
                ordersIn2015
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
    public void any() {
        List<String> result =
            from(people)
                .where(collection(Person::pets).any(pet -> pet.age() > 6))
                .select(pers -> pers.name())
                .list();
    
        assertEquals(
                asList("Rui"),
                result
        );
    }
```
