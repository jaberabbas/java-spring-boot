package com.entity.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Table(name = "department", uniqueConstraints = {
    //@UniqueConstraint(columnNames = {"department_name"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(targetEntity = Employee.class, mappedBy = "department")
    private Set<Employee> employees;

}
