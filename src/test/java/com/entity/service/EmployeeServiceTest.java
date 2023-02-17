package com.entity.service;

import com.entity.dao.DepartmentRepository;
import com.entity.model.Department;
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
    private DepartmentService departmentService;

    @Mock
    DepartmentRepository departmentRepository;


    @Test
    public void testCreate() {
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Paris", null);
        when(departmentRepository.save(department)).thenReturn(department);
        Department savedDept = departmentService.create(department);
        assertThat(savedDept.getName()).isEqualTo("dept");
        assertThat(savedDept.getLocation()).isEqualTo("Paris");
    }

    @Test
    public void testGet() {
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Paris", null);
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        Optional<Department> optionalDepartment = departmentService.findById(Long.valueOf(1));
        if (optionalDepartment.isPresent()) {
            Department department1 = optionalDepartment.get();
            assertThat(department1.getName()).isEqualTo(department.getName());
            assertThat(department1.getLocation()).isEqualTo(department.getLocation());
        }
    }

    @Test
    public void testGetAll() {
        Department department1 = new Department(Long.valueOf(1), "dept1", "department service unit test", "Paris", null);
        Department department2 = new Department(Long.valueOf(2), "dept2", "department service unit test", "London", null);
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(department1);
        departmentList.add(department2);
        Page<Department> departmentPage = new PageImpl<>(departmentList);
        when(departmentRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(departmentPage);
        Pageable pageable = PageRequest.of(1, 2);
        Page<Department> departmentPage1 = departmentService.findAll(pageable);
        Object[] depts = departmentPage1.stream().toArray();
        Department dept1 = (Department) depts[0];
        Department dept2 = (Department) depts[1];
        assertThat(dept1.getName()).isEqualTo(department1.getName());
        assertThat(dept1.getLocation()).isEqualTo(department1.getLocation());
        assertThat(dept2.getName()).isEqualTo(department2.getName());
        assertThat(dept2.getLocation()).isEqualTo(department2.getLocation());
    }

    @Test
    public void testDelete() {
        Department department = new Department(Long.valueOf(1), "dept", "department service unit test", "Paris", null);
        departmentService.delete(department);
    }
}
