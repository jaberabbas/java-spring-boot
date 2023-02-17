package com.entity.dao;

import com.entity.model.Department;
import com.entity.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositorySliceTest {


    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testSaveFindById(){
        Department department = new Department(null, "dept_123", "department created from junit", "Brussels", null);
        departmentRepository.save(department);
        List<Department> departmentList = departmentRepository.findDepartmentByName(department.getName());
        Employee employee = new Employee(null, "James", "Bond", Double.valueOf(10000), 55, "Agent", departmentList.get(0));
        employeeRepository.save(employee);
        List<Employee> employeeList =  employeeRepository.findByFirstNameAndLastName("James", "Bond");
        Employee savedEmployee = employeeList.get(0);
        Optional<Employee> optionalEmployee = employeeRepository.findById(savedEmployee.getId());//the goal is to test getById because this is what I use in the code, not get by name
        if(optionalEmployee.isPresent()) System.out.println(optionalEmployee.get());
        assertThat(optionalEmployee.get().getFirstName()).isEqualTo("James");
        assertThat((optionalEmployee.get().getLastName())).isEqualTo("Bond");
    }

    @Test
    public void testFindAll(){
        Department department1 = new Department(null, "dept_1", "department created from junit", "Brussels", null);
        Department department2 = new Department(null, "dept_2", "department created from junit", "Brussels", null);
        departmentRepository.save(department1);
        departmentRepository.save(department2);

        List<Department> departmentList1 = departmentRepository.findDepartmentByName(department1.getName());
        List<Department> departmentList2= departmentRepository.findDepartmentByName(department2.getName());

        Employee employee1 = new Employee(null, "James", "Bond", Double.valueOf(10000), 55, "Agent", departmentList1.get(0));
        Employee employee2 = new Employee(null, "Mister", "Bean", Double.valueOf(10000), 55, "Agent", departmentList2.get(0));

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        List<Employee> employeeList = employeeRepository.findAll();
        employeeList.forEach(employee ->

                {
                    assertThat(employee.getFirstName().length()).isGreaterThan(0);
                    assertThat(employee.getLastName().length()).isGreaterThan(0);
                    assertThat(employee.getAge()).isGreaterThan(18);
                    System.out.println(employee.getFirstName() + " " + employee.getLastName());
                }

        );
    }

    @Test
    public void testUpdate(){
        Department department = new Department(null, "dept_123", "department created from junit", "Brussels", null);
        departmentRepository.save(department);
        List<Department> departmentList = departmentRepository.findDepartmentByName(department.getName());
        Employee employee = new Employee(null, "James", "Bond", Double.valueOf(10000), 55, "Agent", departmentList.get(0));
        employeeRepository.save(employee);
        List<Employee> employeeList =  employeeRepository.findByFirstNameAndLastName("James", "Bond");
        Employee savedEmployee = employeeList.get(0);
        Optional<Employee> optionalEmployee = employeeRepository.findById(savedEmployee.getId());//the goal is to test getById because this is what I use in the code, not get by name
        if(optionalEmployee.isPresent()){
            System.out.println(optionalEmployee.get());
            Employee employeeToUpdate = optionalEmployee.get();
            employeeToUpdate.setAge(60);
            employeeRepository.save(employeeToUpdate);
            Optional<Employee> updatedEmployee = employeeRepository.findById(employeeToUpdate.getId());
            assertThat(updatedEmployee.get().getAge()).isEqualTo(60);
        }
    }

    @Test
    public void testDelete(){
        Department department = new Department(null, "dept_123", "department created from junit", "Brussels", null);
        departmentRepository.save(department);
        List<Department> departmentList = departmentRepository.findDepartmentByName(department.getName());
        Employee employee = new Employee(null, "James", "Bond", Double.valueOf(10000), 55, "Agent", departmentList.get(0));
        employeeRepository.save(employee);
        List<Employee> employeeList =  employeeRepository.findByFirstNameAndLastName("James", "Bond");
        Employee savedEmployee = employeeList.get(0);
        Optional<Employee> optionalEmployee = employeeRepository.findById(savedEmployee.getId());//the goal is to test getById because this is what I use in the code, not get by name
        if(optionalEmployee.isPresent()){
            System.out.println(optionalEmployee.get());
            Employee employeeToDelete = optionalEmployee.get();
            employeeRepository.delete(employeeToDelete);
            Optional<Employee> optionalDeletedEmployee = employeeRepository.findById(employeeToDelete.getId());
            assertThat(optionalDeletedEmployee.isPresent()).isEqualTo(false);
        }

    }
}
