package com.benjiweber.linq;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ForwardingCollection<T> extends Collection<T> {

    Collection<T> delegate();

    @Override
    default int size() {
        return delegate().size();
    }

    @Override
    default boolean isEmpty() {
        return delegate().isEmpty();
    }

    @Override
    default boolean contains(Object o) {
        return delegate().contains(o);
    }

    @Override
    default Iterator<T> iterator() {
        return delegate().iterator();
    }

    @Override
    default Object[] toArray() {
        return delegate().toArray();
    }

    @Override
    default <T1> T1[] toArray(T1[] a) {
        return delegate().toArray(a);
    }

    @Override
    default boolean add(T t) {
        return delegate().add(t);
    }

    @Override
    default boolean remove(Object o) {
        return delegate().remove(o);
    }

    @Override
    default boolean containsAll(Collection<?> c) {
        return delegate().containsAll(c);
    }

    @Override
    default boolean addAll(Collection<? extends T> c) {
        return delegate().addAll(c);
    }

    @Override
    default boolean removeAll(Collection<?> c) {
        return delegate().removeAll(c);
    }

    @Override
    default boolean removeIf(Predicate<? super T> filter) {
        return delegate().removeIf(filter);
    }

    @Override
    default boolean retainAll(Collection<?> c) {
        return delegate().retainAll(c);
    }

    @Override
    default void clear() {
        delegate().clear();
    }

    @Override
    default Spliterator<T> spliterator() {
        return delegate().spliterator();
    }

    @Override
    default Stream<T> stream() {
        return delegate().stream();
    }

    @Override
    default Stream<T> parallelStream() {
        return delegate().parallelStream();
    }

    @Override
    default void forEach(Consumer<? super T> action) {
        delegate().forEach(action);
    }
}
