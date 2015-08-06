package com.benjiweber.linq;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public interface PropertyPredicate {
    interface PropertyComparison<T,U> {
        Predicate<T> equalTo(U value);
        Predicate<T> lessThan(U value);
        Predicate<T> greaterThan(U value);
    }

    static <T, U extends Comparable> PropertyComparison<T,U> property(Function<T, U> getter) {
        return new PropertyComparison<T, U>() {
            public Predicate<T> equalTo(U value) {
                return item -> Objects.equals(getter.apply(item), value);
            }
            public Predicate<T> lessThan(U value) {
                return item -> getter.apply(item).compareTo(value) < 0;
            }
            public Predicate<T> greaterThan(U value) {
                return item -> getter.apply(item).compareTo(value) > 0;
            }
        };
    }
}
