package com.example.mockserver;

import lombok.Data;

// Simple POJO to represent the product object
@Data
public class Product {
    private int id;
    private String name;
    private double price;
    private boolean bestSeller;
}