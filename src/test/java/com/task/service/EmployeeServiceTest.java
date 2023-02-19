package com.task.service;

import com.task.model.Department;
import com.task.model.Employee;
import com.task.util.WebClientConnector;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private WebClientConnector webClientConnector;

    @Test
    public void testCreate(){
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Bruxelles");
        Employee employee = new Employee(Long.valueOf(1), "James", "Bond", Double.valueOf(6000), 43, "Manager", department);
        HttpEntity<Employee> request = new HttpEntity<>(employee);
        URI location = URI.create("http://localhost/employee/1");
        when(webClientConnector.create("/employee", request)).thenReturn(ResponseEntity.created(location).body(employee));
        ResponseEntity responseEntity = employeeService.create(employee);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(201);
        Employee savedEmpl =  (Employee) responseEntity.getBody();
        assertThat(savedEmpl.getFirstName()).isEqualTo("James");
        assertThat((savedEmpl.getLastName())).isEqualTo("Bond");
        assertThat(savedEmpl.getSalary()).isEqualTo(6000);
        assertThat(savedEmpl.getJobTitle()).isEqualTo("Manager");
        assertThat(savedEmpl.getDepartment().getName()).isEqualTo("dept");
        assertThat(savedEmpl.getDepartment().getLocation()).isEqualTo("Bruxelles");
    }

    @Test
    public void testGet(){
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Bruxelles");
        Employee employee = new Employee(Long.valueOf(1), "James", "Bond", Double.valueOf(6000), 43, "Manager", department);
        when(webClientConnector.getById(anyString())).thenReturn(ResponseEntity.ok(employee));
        ResponseEntity responseEntity =  employeeService.getById(Long.valueOf(1));
        Employee savedEmpl = (Employee) responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(savedEmpl.getFirstName()).isEqualTo("James");
        assertThat((savedEmpl.getLastName())).isEqualTo("Bond");
        assertThat(savedEmpl.getSalary()).isEqualTo(6000);
        assertThat(savedEmpl.getJobTitle()).isEqualTo("Manager");
        assertThat(savedEmpl.getDepartment().getName()).isEqualTo("dept");
        assertThat(savedEmpl.getDepartment().getLocation()).isEqualTo("Bruxelles");
    }

    @Test
    public void testGetAll(){
        Department department1 = new Department(Long.valueOf(1), "dept1", "department service unit test", "Paris");
        Department department2 = new Department(Long.valueOf(2), "dept2", "department service unit test", "NY");
        Employee employee1 = new Employee(Long.valueOf(1), "James", "Bond", Double.valueOf(6000), 43, "Manager", department1);
        Employee employee2 = new Employee(Long.valueOf(2), "Mister", "Beens", Double.valueOf(5000), 56, "Sales Man", department2);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        ResponseEntity responseEntity = ResponseEntity.ok(employeeList);
        when(webClientConnector.getAll("/employee")).thenReturn(responseEntity);
        ResponseEntity responseEntity1 = employeeService.getAll();
        List<Employee> employeeList1 = (List<Employee>) responseEntity1.getBody();
        assertThat(responseEntity1.getStatusCode().value()).isEqualTo(200);
        assertThat(employeeList1.get(0).getFirstName()).isEqualTo("James");
        assertThat(employeeList1.get(0).getLastName()).isEqualTo("Bond");
        assertThat(employeeList1.get(0).getDepartment().getDescription()).isEqualTo("department service unit test");
        assertThat(employeeList1.get(1).getFirstName()).isEqualTo("Mister");
        assertThat(employeeList1.get(1).getLastName()).isEqualTo("Beens");
        assertThat(employeeList1.get(1).getDepartment().getDescription()).isEqualTo("department service unit test");
    }

    @Test
    public void testUpdate(){
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Bruxelles");
        Employee employee = new Employee(Long.valueOf(1), "James", "Bond", Double.valueOf(6000), 43, "Manager", department);
        HttpEntity<Employee> request = new HttpEntity<>(employee);
        when(webClientConnector.update("/employee/1", request)).thenReturn(ResponseEntity.noContent().build());
        ResponseEntity responseEntity =  employeeService.update(Long.valueOf(1), employee);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(204);
    }

    @Test
    public void testDelete(){
        when(webClientConnector.delete(anyString())).thenReturn(ResponseEntity.noContent().build());
        ResponseEntity responseEntity =  employeeService.delete(Long.valueOf(1));
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(204);
    }
}
