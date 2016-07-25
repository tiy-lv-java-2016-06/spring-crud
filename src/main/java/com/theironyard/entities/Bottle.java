package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by vasantia on 7/21/16.
 */

@Entity
@Table(name = "bottles")
public class Bottle {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private int vintage;

    @Column(nullable = false)
    private String variety;

    @Column(nullable = false)
    private float abv;

    @ManyToOne
    User user;

    public Bottle() {
    }

    public Bottle(String name, String producer, String region, int vintage, String variety, float abv, User user) {
        this.name = name;
        this.producer = producer;
        this.region = region;
        this.vintage = vintage;
        this.variety = variety;
        this.abv = abv;
        this.user = user;
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

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getVintage() {
        return vintage;
    }

    public void setVintage(int vintage) {
        this.vintage = vintage;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public float getAbv() {
        return abv;
    }

    public void setAbv(float abv) {
        this.abv = abv;
    }
}
