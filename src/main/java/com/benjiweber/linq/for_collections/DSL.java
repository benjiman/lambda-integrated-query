package com.benjiweber.linq.for_collections;

import java.util.Collection;

public class DSL {

    public static <T> CollectionLinq<T> from(Collection<T> collection) {
        return () -> collection;
    }

}
