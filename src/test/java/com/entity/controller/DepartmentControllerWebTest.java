package com.entity.controller;

import com.entity.dao.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentControllerWebTest {
    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void testCreate() {
        URI location = testRestTemplate.postForLocation("/department", new Department(null, "dept 1", "dept created from controller test", "Brussels", null));
        Department department = testRestTemplate.getForObject(location, Department.class);
        assertThat(department.getName()).isEqualTo("dept 1");
        assertThat(department.getLocation()).isEqualTo("Brussels");
        assertThat(department.getDescription()).contains("controller");
    }

    @Test
    public void testGet(){
        Department department = testRestTemplate.getForObject("/department/{id}", Department.class, 2);
/*        assertThat(department.getName()).isEqualTo("daily banking");
        assertThat(department.getLocation()).isEqualTo("Bruxelles");
        assertThat(department.getDescription()).contains("banking");*/
    }

/*    @Test
    public void testGetAll(){
        Department[] departmentList = testRestTemplate.getForObject("/department", Department[].class);
        System.out.println("length: " + departmentList.length);
    }*/
}
