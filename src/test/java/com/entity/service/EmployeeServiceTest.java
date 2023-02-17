package com.entity.service;

import com.entity.dao.EmployeeRepository;
import com.entity.model.Department;
import com.entity.model.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    EmployeeRepository employeeRepository;


    @Test
    public void testCreate() {
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Paris", null);
        Employee employee = new Employee(Long.valueOf(1), "Bart", "Vanden", Double.valueOf(2000), 35, "Architect", department);
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee savedEmpl = employeeService.create(employee);
        assertThat(savedEmpl.getFirstName()).isEqualTo("Bart");
        assertThat(savedEmpl.getLastName()).isEqualTo("Vanden");
        assertThat(savedEmpl.getDepartment().getId()).isEqualTo(1);
        assertThat(savedEmpl.getDepartment().getLocation()).isEqualTo(department.getLocation());
    }

    @Test
    public void testGet() {
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Paris", null);
        Employee employee = new Employee(Long.valueOf(1), "Bart", "Vanden", Double.valueOf(2000), 35, "Architect", department);
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        Optional<Employee> optionalEmpl = employeeService.findById(Long.valueOf(1));
        if (optionalEmpl.isPresent()) {
            Employee employee1 = optionalEmpl.get();
            assertThat(employee1.getFirstName()).isEqualTo(employee.getFirstName());
            assertThat(employee1.getLastName()).isEqualTo(employee.getLastName());
            assertThat(employee1.getDepartment().getName()).isEqualTo(department.getName());
        }
    }

    @Test
    public void testGetAll() {
        Department department1 = new Department(Long.valueOf(1), "dept1", "department service unit test", "Paris", null);
        Department department2 = new Department(Long.valueOf(2), "dept2", "department service unit test", "London", null);
        Employee employee1 = new Employee(Long.valueOf(1), "Bart", "Vanden", Double.valueOf(2000), 35, "Architect", department1);
        Employee employee2 = new Employee(Long.valueOf(2), "Bart", "Vanden", Double.valueOf(2000), 35, "Architect", department2);


        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        Page<Employee> employeePage = new PageImpl<>(employeeList);
        when(employeeRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(employeePage);
        Pageable pageable = PageRequest.of(1, 2);
        Page<Employee> employeePage1 = employeeService.findAll(pageable);
        Object[] empls = employeePage1.stream().toArray();
        Employee empl1 = (Employee) empls[0];
        Employee empl2 = (Employee) empls[1];
        assertThat(empl1.getFirstName()).isEqualTo(empl1.getFirstName());
        assertThat(empl1.getLastName()).isEqualTo(empl1.getLastName());
        assertThat(empl2.getAge()).isEqualTo(empl2.getAge());
        assertThat(empl2.getSalary()).isEqualTo(empl2.getSalary());
    }

    @Test
    public void testDelete() {
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Paris", null);
        Employee employee = new Employee(Long.valueOf(1), "Bart", "Vanden", Double.valueOf(2000), 35, "Architect", department);
        employeeService.delete(employee);
    }
}
