package com.benjiweber.linq;

import java.util.function.Function;

public interface GroupByOperations<T> {
    <K> Group<K,T> groupBy(Function<T, K> keyExtractor);
}
