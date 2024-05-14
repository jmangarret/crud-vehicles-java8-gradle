package com.example.vehicles.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="VEHICLE")
public class Vehicle implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    private String model;
    private String license;
    private String color;
    private Integer releaseYear;

    public Vehicle(Integer id, String brand, String model, String license, String color, Integer releaseYear) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.license = license;
        this.color = color;
        this.releaseYear = releaseYear;
    }

    public Vehicle() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id.toString() +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", license='" + license + '\'' +
                ", color='" + color + '\'' +
                ", releaseYear='" + releaseYear.toString() + '\'' +
                '}';
    }
}
