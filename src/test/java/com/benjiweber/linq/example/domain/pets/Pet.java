package com.benjiweber.linq.example.domain.pets;


public interface Pet {
    int age();
    String name();
    static Pet pet(int age) { return pet("", age); }
    static Pet pet(String name, int age) {
        return new Pet() {
            public int age() { return age; }
            public String name() { return name; }
        };
    }
}