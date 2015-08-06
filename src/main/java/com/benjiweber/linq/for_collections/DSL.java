package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.CollectionPredicate;
import com.benjiweber.linq.PropertyPredicate;
import com.benjiweber.linq.tuples.Tuple;

import java.util.Collection;
import java.util.Objects;
import java.util.function.*;

public class DSL {

    public static <T> CollectionLinq<T> from(Collection<T> collection) {
        return () -> collection;
    }

    public static <T,U> Predicate<Tuple<T,U>> match(BiPredicate<T,U> explodedMatcher) {
        return tuple -> explodedMatcher.test(tuple.one(), tuple.two());
    }

    public static <T,U,R> Function<Tuple<T,U>, R> into(BiFunction<T,U,R> explodedMapper) {
        return tuple -> explodedMapper.apply(tuple.one(), tuple.two());
    }

    public static <T,U> Consumer<Tuple<T,U>> of(BiConsumer<T,U> explodedConsumer) {
        return tuple -> explodedConsumer.accept(tuple.one(), tuple.two());
    }

    public static BinaryOperator<Integer> sumInt() {
        return (a,b) -> a+b;
    }

    public static BinaryOperator<Long> sum() {
        return (a,b) -> a+b;
    }

    public static Long asLong(Integer integer) {
        return Long.valueOf(integer);
    }

    public static <T, U extends Comparable> PropertyPredicate.PropertyComparison<T,U> property(Function<T, U> getter) {
        return PropertyPredicate.property(getter);
    }

    public static <T, U, V extends Collection<U>> CollectionPredicate.CollectionCondition<T,U,V> collection(Function<T,V> getter) {
        return CollectionPredicate.collection(getter);
    }

}
