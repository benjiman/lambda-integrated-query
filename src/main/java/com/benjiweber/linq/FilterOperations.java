package com.benjiweber.linq;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public interface FilterOperations<T> {
    Linq<T> where(Predicate<T> predicate);

}
