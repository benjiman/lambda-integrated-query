package com.benjiweber.linq;

import java.util.Collection;
import java.util.function.Function;

public class PropertyGetter<T, U extends Comparable> {
    public final Function<T, U> getter;

    private PropertyGetter(Function<T, U> getter) {
        this.getter = getter;
    }

    public static <T, U extends Comparable> PropertyGetter<T, U> property(Function<T, U> getter) {
        return new PropertyGetter<>(getter);
    }
}
