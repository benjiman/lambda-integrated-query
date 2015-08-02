package com.benjiweber.linq;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Linq<T> {
    <R> Linq<R> select(Function<T, R> mapper);
    <R, U extends Collection<R>> Linq<R> selectMany(Function<T, U> mapper);
    <K> Group<K,T> groupBy(Function<T, K> keyExtractor);
    Linq<T> where(Predicate<T> predicate);
    <U> Linq<T> whereEquals(Function<T,U> propertyExtractor, U comparisonValue);
    <U> Linq<T> whereEquals(U comparisonValue, Function<T,U> propertyExtractor);
}
