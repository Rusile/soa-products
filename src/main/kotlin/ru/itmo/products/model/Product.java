package ru.itmo.products.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.time.OffsetDateTime;

public class Product {
    @JsonbProperty("id")
    private Long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @JsonbProperty("name")
    private String name; // Поле не может быть null, Строка не может быть пустой
    @JsonbProperty("coordinates")
    private Coordinates coordinates; // Поле не может быть null
    @JsonbProperty("creationDate")
    private OffsetDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @JsonbProperty("price")
    private double price; // Поле не может быть null, Значение поля должно быть больше 0
    @JsonbProperty("partNumber")
    private String partNumber; // Длина строки не должна быть больше 40, Длина строки должна быть не меньше 17, Поле не может быть null
    @JsonbProperty("unitOfMeasure")
    private UnitOfMeasure unitOfMeasure; // Поле не может быть null
    @JsonbProperty("owner")
    private Person owner; // Поле может быть null

    @JsonbCreator
    public Product(Long id, String name, Coordinates coordinates, OffsetDateTime creationDate, double price, String partNumber, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.partNumber = partNumber;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}

