package com.benjiweber.linq.for_collections;

import java.util.Collection;

public class EqualsHashcodeSupport<T> implements CollectionLinq<T> {
    private final Collection<T> collection;

    private EqualsHashcodeSupport(Collection<T> collection) {
        this.collection = collection;
    }

    public static <T> EqualsHashcodeSupport<T> equalsHashCode(Collection<T> collection) {
        return new EqualsHashcodeSupport<>(collection);
    }

    public Collection<T> delegate() {
        return collection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EqualsHashcodeSupport<?> that = (EqualsHashcodeSupport<?>) o;

        return !(collection != null ? !collection.equals(that.collection) : that.collection != null);

    }

    @Override
    public int hashCode() {
        return collection != null ? collection.hashCode() : 0;
    }

    @Override
    public String toString() {
        return collection.toString();
    }
}
