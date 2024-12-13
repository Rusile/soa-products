package ru.itmo.products.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.Min;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private double x; // Поле не может быть null
    @Min(-110)
    private double y; // Значение поля должно быть больше -110, Поле не может быть null

    public Coordinates() {

    }

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

