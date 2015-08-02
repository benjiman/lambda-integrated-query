package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.ForwardingCollection;
import com.benjiweber.linq.Group;
import com.benjiweber.linq.Linq;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.benjiweber.linq.Group.group;
import static com.benjiweber.linq.for_collections.EqualsHashcodeSupport.equalsHashCode;
import static java.util.stream.Collectors.toCollection;

public interface CollectionLinq<T> extends Linq<T>, ForwardingCollection<T> {

    Collection<T> delegate();

    default <R> CollectionLinq<R> select(Function<T, R> mapper) {
        return streamOp(stream -> stream.map(mapper));
    }

    default <R, U extends Collection<R>> CollectionLinq<R> selectMany(Function<T, U> mapper) {
        return streamOp(stream -> stream.flatMap(item -> mapper.apply(item).stream()));
    }

    default <K> Group<K, T> groupBy(Function<T, K> keyExtractor) {
        return group(delegate()).by(keyExtractor);
    }

    default CollectionLinq<T> where(Predicate<T> predicate) {
        return streamOp(stream -> stream.filter(predicate));
    }

    default <U> CollectionLinq<T> whereEquals(Function<T,U> propertyExtractor, U comparisonValue) {
        return where(item -> Objects.equals(comparisonValue, propertyExtractor.apply(item)));
    }

    default <U> CollectionLinq<T> whereEquals(U comparisonValue, Function<T,U> propertyExtractor) {
        return whereEquals(propertyExtractor, comparisonValue);
    }

    default <R> CollectionLinq<R> streamOp(Function<Stream<T>,Stream<R>> f) {
        Stream<R> stream = f.apply(delegate().stream());
        return equalsHashCode(stream.collect(toCollection(ArrayList::new)));
    }

    default T reduce(T seed, BinaryOperator<T> aggregator) {
        return delegate().stream().reduce(seed, aggregator);
    }

    default <U> U reduce(U seed, BiFunction<U, T, U> accumulator, BinaryOperator<U> combiner) {
        return delegate().stream().reduce(seed, accumulator, combiner);
    }

    default Optional<T> reduce(BinaryOperator<T> reducer) {
        return delegate().stream().reduce(reducer);
    }

    default <U> AggregateInto<U> aggregate(Function<T,U> getter) {
        return aggregator -> select(getter).reduce(aggregator).orElse(null);
    }

    default List<T> list() {
        return new ArrayList(delegate());
    }

    default Optional<T> first() {
        return delegate().size() > 0 ? Optional.of(delegate().iterator().next()) : Optional.empty();
    }
    default Optional<T> last() {
        return delegate().size() > 0 ? Optional.of(list().get(delegate().size() -1)) : Optional.empty();
    }
    default Linq<T> skip(int n) {
        return streamOp(stream -> stream.skip(n));
    }

}
