package com.task.service;

import com.task.model.Department;
import com.task.util.WebClientConnector;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.w3c.dom.stylesheets.LinkStyle;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private WebClientConnector webClientConnector;

    @Test
    public void testCreate(){
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Bruxelles");
        HttpEntity<Department> request = new HttpEntity<>(department);
        URI location = URI.create("http://localhost/department/1");
        when(webClientConnector.create("/department", request)).thenReturn(ResponseEntity.created(location).body(department));
        ResponseEntity responseEntity = departmentService.create(department);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(201);
        Department savedDept =  (Department) responseEntity.getBody();
        assertThat(savedDept.getName()).isEqualTo("dept");
        assertThat((savedDept.getLocation())).isEqualTo("Bruxelles");
        assertThat(savedDept.getDescription()).isEqualTo("department service unit test");
    }

    @Test
    public void testGet(){
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Bruxelles");
        when(webClientConnector.getById(anyString())).thenReturn(ResponseEntity.ok(department));
        ResponseEntity responseEntity =  departmentService.getById(Long.valueOf(1));
        Department savedDept = (Department) responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(savedDept.getName()).isEqualTo("dept");
        assertThat((savedDept.getLocation())).isEqualTo("Bruxelles");
        assertThat(savedDept.getDescription()).isEqualTo("department service unit test");
    }

    @Test
    public void testGetAll(){
        Department department1 = new Department(Long.valueOf(1), "dept1", "department service unit test", "Paris");
        Department department2 = new Department(Long.valueOf(2), "dept2", "department service unit test", "NY");
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(department1);
        departmentList.add(department2);
        ResponseEntity responseEntity = ResponseEntity.ok(departmentList);
        when(webClientConnector.getAll("/department")).thenReturn(responseEntity);
        ResponseEntity responseEntity1 = departmentService.getAll();
        List<Department> departmentList1 = (List<Department>) responseEntity1.getBody();
        assertThat(responseEntity1.getStatusCode().value()).isEqualTo(200);
        assertThat(departmentList1.get(0).getName()).isEqualTo("dept1");
        assertThat(departmentList1.get(0).getLocation()).isEqualTo("Paris");
        assertThat(departmentList1.get(0).getDescription()).isEqualTo("department service unit test");
        assertThat(departmentList1.get(1).getName()).isEqualTo("dept2");
        assertThat(departmentList1.get(1).getLocation()).isEqualTo("NY");
        assertThat(departmentList1.get(1).getDescription()).isEqualTo("department service unit test");
    }

    @Test
    public void testUpdate(){
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Bruxelles");
        HttpEntity<Department> request = new HttpEntity<>(department);
        when(webClientConnector.update("/department/1", request)).thenReturn(ResponseEntity.noContent().build());
        ResponseEntity responseEntity =  departmentService.update(Long.valueOf(1), department);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(204);
    }

    @Test
    public void testDelete(){
        when(webClientConnector.delete(anyString())).thenReturn(ResponseEntity.noContent().build());
        ResponseEntity responseEntity =  departmentService.delete(Long.valueOf(1));
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(204);
    }
}
