package com.benjiweber.linq.example.domain.flowers;

public interface CarnivorousPlant extends Plant {
    String trapType();
    static CarnivorousPlant carnivorousPlant(String name, String trapType) {
        return new CarnivorousPlant() {
            public String trapType() { return trapType; }
            public String name() { return name; }
        };
    }
}
