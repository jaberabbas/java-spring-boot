package com.entity.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "department", uniqueConstraints = {
    //@UniqueConstraint(columnNames = {"department_name"})
})
@Data
public class Department {

    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name", nullable = false)
    private String name;

    @Column(name = "department_description")
    private String description;

    @Column(name = "department_location")
    private String location;

    @OneToMany(targetEntity = Employee.class, mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

}
