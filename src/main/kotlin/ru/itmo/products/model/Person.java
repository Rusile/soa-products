package ru.itmo.products.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.time.OffsetDateTime;

public class Person {
    @JsonbProperty("id")
    private Long id;

    @JsonbProperty("name")
    private String name; // Field cannot be null, String cannot be empty

    @JsonbProperty("birthday")
    private OffsetDateTime birthday; // Field can be null

    @JsonbProperty("weight")
    private Double weight; // Field value must be greater than 0

    @JsonbProperty("eyeColor")
    private Color eyeColor; // Field can be null

    @JsonbProperty("hairColor")
    private Color hairColor; // Field cannot be null

    @JsonbCreator
    public Person(Long id, String name, OffsetDateTime birthday, Double weight, Color eyeColor, Color hairColor) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(OffsetDateTime birthday) {
        this.birthday = birthday;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }
}
