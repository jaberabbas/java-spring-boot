package com.entity.dao;

import com.entity.model.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
public class DepartmentRepositorySliceTest {

 /*   @Autowired
    private TestEntityManager testEntityManager;*/

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testSave() {
        Department department = new Department(null, "dept_123", "department created from junit", "Brussels", null);
        departmentRepository.save(department);
        List<Department> departmentList = departmentRepository.findDepartmentByName(department.getName());
        Department savedDepart = departmentList.get(0);
        System.out.println(savedDepart);
        assertThat(savedDepart.getId()).isNotZero();
        assertThat(savedDepart.getName()).isEqualTo(department.getName());
        assertThat(savedDepart.getDescription()).isEqualTo(department.getDescription());
        assertThat(savedDepart.getLocation()).isEqualTo(department.getLocation());
    }

    @Test
    public void testFindById() {
        Department department = new Department(null, "dept_123", "department created from junit", "Brussels", null);
        departmentRepository.save(department);
        List<Department> departmentList = departmentRepository.findDepartmentByName(department.getName());
        Department savedDep = departmentList.get(0);
        Optional<Department> optionalDepartment = departmentRepository.findById(savedDep.getId());
        Department savedDepart = new Department();
        if (optionalDepartment.isPresent()) savedDepart = optionalDepartment.get();
        System.out.println(savedDepart);
        assertThat(savedDepart.getName()).isEqualTo(department.getName());
        assertThat(savedDepart.getDescription()).isEqualTo(department.getDescription());
        assertThat(savedDepart.getLocation()).isEqualTo(department.getLocation());
    }

    @Test
    public void testFindAll() throws NullPointerException {
        Department department1 = new Department(null, "dept_1", "department created from junit", "Brussels", null);
        Department department2 = new Department(null, "dept_2", "department created from junit", "Brussels", null);
        Department department3 = new Department(null, "dept_3", "department created from junit", "Brussels", null);
        departmentRepository.save(department1);
        departmentRepository.save(department2);
        departmentRepository.save(department3);
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList.size() != 0);
        departmentList.forEach(department -> {
                    assertThat(department.getName().length()).isGreaterThan(0);
                    assertThat(department.getLocation().length()).isGreaterThan(0);
                    System.out.println(department.getName());
                }
        );
    }

    @Test
    public void testUpdate() {
        Department department = new Department(null, "dept_123", "department created from junit", "Brussels", null);
        departmentRepository.save(department);
        List<Department> departmentList = departmentRepository.findDepartmentByName(department.getName());
        Department savedDept = departmentList.get(0);
        Optional<Department> optionalDepartment = departmentRepository.findById(savedDept.getId());
        if (optionalDepartment.isPresent()) {
            Department deptToUpdate = optionalDepartment.get();
            System.out.println(deptToUpdate);
            deptToUpdate.setLocation("Paris");
            departmentRepository.save(deptToUpdate);
        }
        List<Department> departmentList1 = departmentRepository.findDepartmentByName(department.getName());
        Department savedDept1 = departmentList1.get(0);
        Optional<Department> optionalDepartmentUpdated = departmentRepository.findById(savedDept1.getId());
        if (optionalDepartmentUpdated.isPresent()) {
            Department updatedDept = optionalDepartmentUpdated.get();
            assertThat(updatedDept.getLocation()).isEqualTo("Paris");
            System.out.println(updatedDept);
        }
    }

    @Test
    public void testDelete() {
        Department department = new Department(null, "dept_123", "department created from junit", "Brussels", null);
        departmentRepository.save(department);
        List<Department> departmentList = departmentRepository.findDepartmentByName(department.getName());
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentList.get(0).getId());
        if (optionalDepartment.isPresent()) {
            Department deptToDelete = optionalDepartment.get();
            System.out.println(deptToDelete);
            departmentRepository.delete(deptToDelete);
            Optional<Department> deletedOpDept = departmentRepository.findById(deptToDelete.getId());
            assertThat(deletedOpDept.isPresent()).isEqualTo(false);
        }

    }
}
