package com.theironyard;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue
    private int Id;

    @Column(nullable = false)
    private String drivetrain;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @ManyToOne
    private User user;

    public Car() {
    }

    public Car(String drivetrain, String make, String model, int year, User user) {
        this.drivetrain = drivetrain;
        this.make = make;
        this.model = model;
        this.year = year;
        this.user = user;
    }

    public String getDrivetrain() {
        return drivetrain;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getId() {
        return Id;
    }

    public User getUser() {
        return user;
    }

    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setUser(User user) {
        this.user = user;
    }
}