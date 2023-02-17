package com.entity.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee", uniqueConstraints = {
})
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
