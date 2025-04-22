package org.example.module;


import java.io.Serializable;

public class Location implements Validatable , Serializable {
    private Integer x; //Поле не может быть null
    private long y;
    private String name; //Поле может быть null

    /**
     *  @param x    Поле не может быть null
     *  @param name Поле может быть null
     */
    public Location(Integer x, long y, String name) {
        if (x == null) {
            throw new IllegalArgumentException();
        }
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Location() {}

    @Override
    public boolean validate() {
        return x != null;
    }

    public Integer getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public String getName() {
        return name;
    }
}
