package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by EddyJ on 7/21/16.
 */
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String menuType;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int rating;

    @ManyToOne
    User user;

    public Restaurant(String name, String menuType, String address, int rating, User user) {
        this.name = name;
        this.menuType = menuType;
        this.address = address;
        this.rating = rating;
        this.user = user;
    }

    public Restaurant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
