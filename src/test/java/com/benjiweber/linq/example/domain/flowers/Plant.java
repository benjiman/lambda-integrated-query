package com.benjiweber.linq.example.domain.flowers;

public interface Plant {
    String name();
    static Plant plant(String name) { return () -> name; }
}
