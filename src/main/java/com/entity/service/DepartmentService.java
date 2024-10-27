package com.entity.service;

import java.util.Optional;

import com.entity.model.Department;
import com.entity.dao.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department create(Department department) {
        return departmentRepository.save(department);
    }

    public Optional<Department> findById(long id) {
        return departmentRepository.findById(id);
    }

    public Page<Department> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    public void delete(Department department) {
        departmentRepository.delete(department);
    }

}
