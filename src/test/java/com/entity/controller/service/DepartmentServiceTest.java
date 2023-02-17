package com.entity.controller.service;

import com.entity.dao.DepartmentRepository;
import com.entity.model.Department;
import com.entity.service.DepartmentService;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    DepartmentRepository departmentRepository;


    @Test
    public void testCreate() {
        Department department = new Department(Long.getLong("1"), "dept", "department service unit test", "Paris", null);
        when(departmentRepository.save(department)).thenReturn(department);
        Department savedDept = departmentService.create(department);
        assertThat(savedDept.getName()).isEqualTo("dept");
        assertThat(savedDept.getLocation()).isEqualTo("Paris");
    }

    @Test
    public void testGet() {
        Department department = new Department(Long.getLong("1"), "dept", "department service unit test", "Paris", null);
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        Optional<Department> optionalDepartment = departmentRepository.findById(Long.getLong("1"));
        if(optionalDepartment.isPresent()) {
            Department department1 = optionalDepartment.get();
            assertThat(department1.getName()).isEqualTo(department.getName());
            assertThat(department1.getLocation()).isEqualTo(department.getLocation());
        }
    }

}
