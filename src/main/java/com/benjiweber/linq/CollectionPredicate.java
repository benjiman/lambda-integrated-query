package com.benjiweber.linq;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public interface CollectionPredicate {

    interface CollectionCondition<T,U,V> {
        Predicate<T> all(Predicate<U> condition);
        Predicate<T> any(Predicate<U> condition);
        Predicate<T> contains(U item);
    }
    static <T, U, V extends Collection<U>> CollectionCondition<T, U, V> collection(Function<T, V> getter) {
        return new CollectionCondition<T, U, V>() {
            public Predicate<T> all(Predicate<U> condition) {
                return item -> getter.apply(item).stream().allMatch(condition);
            }
            public Predicate<T> any(Predicate<U> condition) {
                return item -> getter.apply(item).stream().anyMatch(condition);
            }
            public Predicate<T> contains(U comparison) {
                return item -> getter.apply(item).stream().filter(i -> Objects.equals(comparison, i)).findAny().map(i->true).orElse(false);
            }
        };
    }
}
