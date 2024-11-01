package ru.itmo.products.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class Person {
    @JsonbProperty("id")
    private Long id;

    @JsonbProperty("name")
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @JsonbProperty("birthday")
    private LocalDateTime birthday;

    @JsonbProperty("weight")
    @NotNull(message = "Weight cannot be null")
    @Positive(message = "Weight must be greater than 0")
    private Double weight;

    @JsonbProperty("eyeColor")
    private Color eyeColor;

    @JsonbProperty("hairColor")
    @NotNull(message = "Hair color cannot be null")
    private Color hairColor;


    @JsonbCreator
    public Person(Long id, String name, LocalDateTime birthday, Double weight, Color eyeColor, Color hairColor) {
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

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
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
