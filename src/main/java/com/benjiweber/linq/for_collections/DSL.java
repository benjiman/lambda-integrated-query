package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.tuples.Tuple;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

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

}
