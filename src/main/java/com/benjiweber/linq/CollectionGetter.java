package com.benjiweber.linq;

import java.util.Collection;
import java.util.function.Function;

public class CollectionGetter<T, U, V extends Collection<U>> {
    public final Function<T, V> collectionGetter;

    private CollectionGetter(Function<T, V> collectionGetter) {
        this.collectionGetter = collectionGetter;
    }

    public static <T, U, V extends Collection<U>> CollectionGetter<T, U, V> collection(Function<T, V> collectionGetter) {
        return new CollectionGetter<>(collectionGetter);
    }
}
