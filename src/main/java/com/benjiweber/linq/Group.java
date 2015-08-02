package com.benjiweber.linq;


import com.benjiweber.linq.for_collections.CollectionLinq;
import com.benjiweber.linq.tuples.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static com.benjiweber.linq.tuples.Tuple.tuple;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

public class Group<T,U> implements ForwardingCollection<Tuple<T,List<U>>>, CollectionLinq<Tuple<T,List<U>>> {
    private final List<Tuple<T,List<U>>> backingList;

    public Group(List<Tuple<T, List<U>>> backingList) {
        this.backingList = backingList;
    }

    public List<Tuple<T,List<U>>> list() {
        return backingList;
    }

    public static <T,U> Group<T,U> newGroup(List<Tuple<T,List<U>>> backingList) {
        return new Group<>(backingList);
    }

    public <V> Group<T, Group<V,U>> thenBy(Function<U,V> keyExtractor) {
        return newGroup(
                list()
                        .stream()
                        .map(tuple -> tuple(tuple.one(), asList(group(tuple.two()).by(keyExtractor)))
                        ).collect(toList())
        );

    }

    public Collection<Tuple<T, List<U>>> delegate() {
        return list();
    }

    public interface By<T> {
        <U> Group<U, T> by(Function<T, U> keyExtractor);
    }

    public static <T,U> By<T> group(Collection<T> ts) {
        return new By<T>() {
            public <U> Group<U, T> by(Function<T, U> keyExtractor) {
                return newGroup(ts.stream()
                        .collect(groupingBy(keyExtractor))
                        .entrySet()
                        .stream()
                        .map(entry -> tuple(entry.getKey(), entry.getValue()))
                        .collect(toList()));
            }
        };
    }

    @Override
    public String toString() {
        return "[" + list().stream().map(Object::toString).collect(joining(",")) + "]";
    }
}
