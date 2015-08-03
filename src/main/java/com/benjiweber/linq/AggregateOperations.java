package com.benjiweber.linq;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public interface AggregateOperations<T> {
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
    default <U extends Number & Comparable<U>> U max(Function<T,U> numberGetter) {
        return select(numberGetter).reduce((a, b) -> a.compareTo(b) > 0 ? a : b).orElse((U)(Number)0);
    }
    default <U extends Number & Comparable<U>> U min(Function<T,U> numberGetter) {
        return select(numberGetter).reduce((a, b) -> a.compareTo(b) < 0 ? a : b).orElse((U)(Number)0);
    }
    default Double sumDouble(Function<T,Double> numberGetter) {
        return select(numberGetter).reduce((a,b) -> a+b).orElse(0D);
    }
    <R> Linq<R> select(Function<T, R> mapper);
    static BinaryOperator<Long> sum = (a,b) -> a+b;
}
