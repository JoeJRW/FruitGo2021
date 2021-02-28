package com.wzh.fruitgo.Bean;

public class Water {
    private Long id;
    private int number;
    private String name;

    public Water(Long id, int number, String name) {
        this.id = id;
        this.number = number;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
