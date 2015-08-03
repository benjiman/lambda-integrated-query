package com.benjiweber.linq;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public interface FilterOperations<T> {
    Linq<T> where(Predicate<T> predicate);
    interface CollectionCondition<T,U,V> {
        Linq<T> all(Predicate<U> condition);
        Linq<T> any(Predicate<U> condition);
        Linq<T> contains(U item);
    }
    <U,V extends Collection<U>> CollectionCondition<T,U,V> whereAggregate(Function<T,V> collectionGetter);
    <U> Linq<T> whereEquals(Function<T,U> propertyExtractor, U comparisonValue);
    <U> Linq<T> whereEquals(U comparisonValue, Function<T,U> propertyExtractor);
    interface PropertyComparison<T,U> {
        Linq<T> equalTo(U value);
        Linq<T> lessThan(U value);
        Linq<T> greaterThan(U value);
    }
    <U extends Comparable<U>> PropertyComparison<T,U> whereProperty(Function<T,U> propertyExtractor);
}
