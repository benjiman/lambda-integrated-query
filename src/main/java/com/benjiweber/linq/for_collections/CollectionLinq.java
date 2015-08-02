package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.ForwardingCollection;
import com.benjiweber.linq.Group;
import com.benjiweber.linq.Linq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.benjiweber.linq.Group.group;
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
        return () -> stream.collect(toCollection(ArrayList::new));
    }

    default List<T> list() {
        return new ArrayList(delegate());
    }
}
