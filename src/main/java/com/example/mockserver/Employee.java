package com.example.mockserver;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Employee {
    private int id;
    private String name;
    private String department;
    private Double salary;
}
