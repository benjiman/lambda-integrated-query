package com.benjiweber.linq.example.domain.pets;

import java.util.List;

import static java.util.Arrays.asList;

public interface Person {
    String name();
    List<Pet> pets();
    static Person person(String name, Pet... pets) {
        return new Person(){
            public String name() { return name; }
            public List<Pet> pets() { return asList(pets); }
        };
    }
}
