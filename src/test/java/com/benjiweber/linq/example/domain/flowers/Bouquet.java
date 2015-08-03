package com.benjiweber.linq.example.domain.flowers;

import java.util.List;

import static java.util.Arrays.asList;

public interface Bouquet {
    List<String> flowers();
    static Bouquet bouquet(String... flowers) {
        return () -> asList(flowers);
    }
}
