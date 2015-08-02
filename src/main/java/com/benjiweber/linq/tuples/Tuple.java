package com.benjiweber.linq.tuples;

import java.util.function.BiFunction;

public class Tuple<T,U> {
    private final T one;
    private final U two;

    public T one() { return one; }
    public U two() { return two; }

    public Tuple(T one, U two) {
        this.one = one;
        this.two = two;
    }

    public static <T,U> Tuple<T,U> tuple(T t, U u) {
        return new Tuple<>(t, u);
    }

    public <V,W> Tuple<V,W> map(BiFunction<T,U,Tuple<V,W>> mapper) {
        return mapper.apply(one(), two());
    }

    @Override
    public String toString() {
        return "(" + one() + ", " + two() + ")";
    }
}