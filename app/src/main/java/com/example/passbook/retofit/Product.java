package com.example.passbook.retofit;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    private String title;
    private String description;
    private double price;
    private String image;
    private boolean isFavorite = false;


    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImage() { return image; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }



}
