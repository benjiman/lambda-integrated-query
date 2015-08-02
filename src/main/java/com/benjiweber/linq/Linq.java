package com.benjiweber.linq;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Linq<T> {
    <R> Linq<R> select(Function<T, R> mapper);
    <R, U extends Collection<R>> Linq<R> selectMany(Function<T, U> mapper);
    <K> Group<K,T> groupBy(Function<T, K> keyExtractor);
    Linq<T> where(Predicate<T> predicate);
    <U> Linq<T> whereEquals(Function<T,U> propertyExtractor, U comparisonValue);
    <U> Linq<T> whereEquals(U comparisonValue, Function<T,U> propertyExtractor);
    Optional<T> reduce(BinaryOperator<T> reducer);
    <U> U reduce(U seed, BiFunction<U, T, U> accumulator, BinaryOperator<U> combiner);
    T reduce(T seed, BinaryOperator<T> aggregator);
    interface AggregateInto<U> {
        U into(BinaryOperator<U> aggregatorFunction);
    }
    <U> AggregateInto<U> aggregate(Function<T,U> getter);
    default Long count() {
        return select(item -> 1L).reduce(sum).orElse(0L);
    }
    default Long sum(Function<T,Long> numberGetter) {
        return select(numberGetter).reduce(sum).orElse(0L);
    }
    default Long max(Function<T,Long> numberGetter) {
        return select(numberGetter).reduce((a, b) -> a > b ? a : b).orElse(0L);
    }
    default Long min(Function<T,Long> numberGetter) {
        return select(numberGetter).reduce((a, b) -> a < b ? a : b).orElse(0L);
    }

    static BinaryOperator<Long> sum = (a,b) -> a+b;

}
