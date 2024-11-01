package ru.itmo.products.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class Coordinates {
    private final double x; // Поле не может быть null
    private final double y; // Значение поля должно быть больше -110, Поле не может быть null

    @JsonbCreator
    public Coordinates(@JsonbProperty("x") double x, @JsonbProperty("y") double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

