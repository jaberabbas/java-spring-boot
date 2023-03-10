package com.javalab.java_lab.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.javalab.java_lab.dao.Department;
import com.javalab.java_lab.dao.DepartmentRepository;

@Service
public class DepartmentService {

    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    public Page<Department> findAll(Pageable pageable) {
        Page<Department> departmentPage = departmentRepository.findAll(pageable);
        return departmentPage;
    }

    public Department create(Department department)  {
           Department savedDepartment =  departmentRepository.save(department);
            return savedDepartment;
    }

    public Optional<Department> findById(long id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment;
    }
    public void delete(Department department) {
        departmentRepository.delete(department);
    }

}
