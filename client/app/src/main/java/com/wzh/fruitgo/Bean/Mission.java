package com.wzh.fruitgo.Bean;

import androidx.annotation.NonNull;

public class Mission {
    private String name;
    private String duration;

    public Mission(String name, String duration){
        this.name=name;
        this.duration=duration;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
