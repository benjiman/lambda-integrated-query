package com.benjiweber.linq;

import com.benjiweber.linq.tuples.Tuple;

import java.security.DigestInputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.*;

public interface Linq<T> extends ProjectionOperations<T>, GroupByOperations<T>, FilterOperations<T>, AggregateOperations<T> {
    Optional<T> reduce(BinaryOperator<T> reducer);
    <U> U reduce(U seed, BiFunction<U, T, U> accumulator, BinaryOperator<U> combiner);
    T reduce(T seed, BinaryOperator<T> aggregator);

    default T first(T orElse) {
        return first().orElse(orElse);
    }
    default T last(T orElse) {
        return last().orElse(orElse);
    }
    Optional<T> first();
    Optional<T> last();
    Linq<T> skip(int n);

    <U> Linq<Tuple<T,U>> from(Function<T, Collection<U>> joiner);
    <U> Linq<Tuple<T,U>> from(Collection<U> toJoin);
    interface JoinCondition<A,B> {
        Linq<Tuple<A,B>> on(BiPredicate<A,B> condition);
    }
    <U> JoinCondition<T,U> join(Collection<U> toJoin);

    <U extends Comparable<U>> Linq<T> orderBy(Function<T, U> sortProperty);
    <U> Linq<T> orderBy(Function<T, U> sortProperty, Comparator<U> comparator);

    <R extends T> Linq<R> ofType(Class<R> type);
}
