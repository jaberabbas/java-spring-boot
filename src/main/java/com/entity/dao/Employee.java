package com.entity.dao;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employee", uniqueConstraints = {
})
@Data
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lombok for getter & setter readability;
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "job_title")
    private String jobTitle;

    //@ManyToOne(targetEntity = Department.class)
    @ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

/*    @Column(name = "department_id", nullable = true)
    private Long departmentId;*/
}
