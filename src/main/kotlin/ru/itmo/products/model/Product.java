package ru.itmo.products.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.itmo.products.util.CustomDateDeserializer;
import ru.itmo.products.util.CustomDateSerializer;

import java.time.LocalDateTime;

public class Product {
    @JsonbProperty("id")
    private Long id;

    @JsonbProperty("name")
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @JsonbProperty("coordinates")
    @NotNull(message = "Coordinates cannot be null")
    private Coordinates coordinates;

    @JsonbProperty("creationDate")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDateTime creationDate;

    @JsonbProperty("price")
    @Positive(message = "Price must be greater than 0")
    private double price;

    @JsonbProperty("partNumber")
    @NotNull(message = "Part number cannot be null")
    @Size(min = 17, max = 40, message = "Part number must be between 17 and 40 characters")
    private String partNumber;

    @JsonbProperty("unitOfMeasure")
    @NotNull(message = "Unit of measure cannot be null")
    private UnitOfMeasure unitOfMeasure;

    @Valid
    @JsonbProperty("owner")
    private Person owner;

    public Product() {

    }

    @JsonbCreator
    public Product(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, double price, String partNumber, UnitOfMeasure unitOfMeasure, Person owner) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", partNumber='" + partNumber + '\'' +
                ", unitOfMeasure=" + unitOfMeasure +
                ", owner=" + owner +
                '}';
    }
}

