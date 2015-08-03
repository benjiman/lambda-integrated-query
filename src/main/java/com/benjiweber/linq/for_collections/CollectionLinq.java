package com.benjiweber.linq.for_collections;

import com.benjiweber.linq.ForwardingCollection;
import com.benjiweber.linq.Group;
import com.benjiweber.linq.Linq;
import com.benjiweber.linq.tuples.Tuple;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import static com.benjiweber.linq.Group.group;
import static com.benjiweber.linq.for_collections.DSL.into;
import static com.benjiweber.linq.for_collections.EqualsHashcodeSupport.equalsHashCode;
import static com.benjiweber.linq.tuples.Tuple.tuple;
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
        return where((Predicate<T>) item -> Objects.equals(comparisonValue, propertyExtractor.apply(item)));
    }

    default <U> CollectionLinq<T> whereEquals(U comparisonValue, Function<T,U> propertyExtractor) {
        return whereEquals(propertyExtractor, comparisonValue);
    }

    interface CollectionCollectionCondition<T,U,V> extends CollectionCondition<T,U,V> {
        CollectionLinq<T> all(Predicate<U> condition);
        CollectionLinq<T> any(Predicate<U> condition);
        CollectionLinq<T> contains(U item);
    }

    default <U,V extends Collection<U>> CollectionCollectionCondition<T,U,V> whereAggregate(Function<T,V> collectionGetter) {
        return new CollectionCollectionCondition<T,U,V>() {
            public CollectionLinq<T> all(Predicate<U> condition) {
                return () ->
                    delegate()
                        .stream()
                        .filter(item ->
                            collectionGetter.apply(item)
                                .stream()
                                .allMatch(condition)
                        ).collect(toCollection(ArrayList::new));
            }
            public CollectionLinq<T> any(Predicate<U> condition) {
                return () ->
                    delegate()
                        .stream()
                        .filter(item ->
                                        collectionGetter.apply(item)
                                                .stream()
                                                .anyMatch(condition)
                        ).collect(toCollection(ArrayList::new));
            }
            public CollectionLinq<T> contains(U item) {
                return any(x -> Objects.equals(x, item));
            }
        };
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
        return delegate().size() > 0 ? Optional.of(list().get(delegate().size() - 1)) : Optional.empty();
    }
    default Linq<T> skip(int n) {
        return streamOp(stream -> stream.skip(n));
    }

    default <U> CollectionLinq<Tuple<T,U>> from(Collection<U> toJoin) {
        return join(toJoin).on((a, b) -> true);
    }
    default <U> CollectionLinq<Tuple<T,U>> from(Function<T, Collection<U>> joiner) {
        return () ->
            select(item -> tuple(item, joiner.apply(item)))
                .streamOp(stream ->
                    stream.flatMap(into((item, items) -> items.stream().map(i2 -> tuple(item, i2))))
                );

    }

    interface CollectionJoinCondition<A,B> extends JoinCondition<A,B> {
        CollectionLinq<Tuple<A,B>> on(BiPredicate<A,B> condition);
    }

    default <U> CollectionJoinCondition<T,U>  join(Collection<U> toJoin) {
        return condition -> streamOp(stream -> stream.flatMap(item -> toJoin.stream().filter(joiningItem -> condition.test(item, joiningItem)).map(joiningItem -> tuple(item, joiningItem))));
    }

    default <U extends Comparable<U>> CollectionLinq<T> sortBy(Function<T,U> sortProperty) {
        return streamOp(
            stream -> stream.sorted(
                (a,b)-> sortProperty.apply(a).compareTo(sortProperty.apply(b))
            )
        );
    }

    default <U> CollectionLinq<T> sortBy(Function<T,U> sortProperty, Comparator<U> comparator) {
        return streamOp(stream -> stream.sorted(
                (a, b) -> comparator.compare(
                        sortProperty.apply(a),
                        sortProperty.apply(b)
                )
        ));
    }

    default <R extends T> CollectionLinq<R> ofType(Class<R> type) {
        return
            where(item -> type.isAssignableFrom(item.getClass()))
                .select(item -> type.cast(item));
    }

}
