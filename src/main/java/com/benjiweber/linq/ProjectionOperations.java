package com.benjiweber.linq;

import java.util.Collection;
import java.util.function.Function;

public interface ProjectionOperations<T> {
    <R> Linq<R> select(Function<T, R> mapper);
    <R, U extends Collection<R>> Linq<R> selectMany(Function<T, U> mapper);
}
