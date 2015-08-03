package com.benjiweber.linq.example.domain.pets;


public interface Pet {
    int age();
    static Pet pet(int age) {
        return () -> age;
    }
}