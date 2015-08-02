package com.benjiweber.linq;

import com.benjiweber.linq.tuples.Tuple;

import java.security.DigestInputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.*;

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
    default T first(T orElse) {
        return first().orElse(orElse);
    }
    default T last(T orElse) {
        return last().orElse(orElse);
    }
    Optional<T> first();
    Optional<T> last();
    Linq<T> skip(int n);
    static BinaryOperator<Long> sum = (a,b) -> a+b;

    <U> Linq<Tuple<T,U>> from(Collection<U> toJoin);
    interface JoinCondition<A,B> {
        Linq<Tuple<A,B>> on(BiPredicate<A,B> condition);
    }
    <U> JoinCondition<T,U> join(Collection<U> toJoin);
}
