package org.example.module;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class Coordinates implements Validatable, Serializable {
    private Integer x; //Максимальное значение поля: 59, Поле не может быть null
    private Double y; //Максимальное значение поля: 115, Поле не может быть null

    /**
     * @param x Максимальное значение поля: 59, Поле не может быть null
     * @param y Максимальное значение поля: 115, Поле не может быть null
     */
    public Coordinates(Integer x, Double y) {
        if (x == null || x > 59 || y == null || y > 115) {
            throw new IllegalArgumentException();
        }
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean validate() {
        if (x > 59)
            return false;
        return y <= 115;
    }

    public Integer getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}
