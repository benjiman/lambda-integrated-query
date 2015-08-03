package com.benjiweber.linq.example.domain.flowers;

public interface Flower {
    String name();
    static Flower flower(String name ) { return () -> name; }
}
