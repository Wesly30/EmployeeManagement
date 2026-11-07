package com.department.service;

import com.department.dto.DepartmentDto;
import com.department.exception.DepartmentNotFoundException;
import com.department.mapper.DepartmentMapper;
import com.department.model.Department;
import com.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentMapper departmentMapper;

    public ResponseEntity<String> addingDepartment(Department department) {
        departmentRepository.save(department);
        return new ResponseEntity<>("Department Added!", HttpStatus.CREATED) ;
    }

    public ResponseEntity<Department> gettingDepartmentById(Integer deptId)  {
         Department departmentWithId = departmentRepository.findById(deptId).orElseThrow(() -> new DepartmentNotFoundException("Department Not Found!"));
         return new ResponseEntity<>(departmentWithId, HttpStatus.OK);
    }

    public ResponseEntity<List<Department>> gettingAllDepartments() {
        try {
            return new ResponseEntity<>(departmentRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updatingDepartment(Integer deptId, DepartmentDto departmentDto) {
        Department existingDepartment = departmentRepository.findById(deptId).orElseThrow(() -> new DepartmentNotFoundException("Department Not Found!"));

        departmentMapper.updateDepartmentFromDto(departmentDto, existingDepartment);

        departmentRepository.save(existingDepartment);
        return new ResponseEntity<>("Department Updated",HttpStatus.OK);

    }

    public ResponseEntity<String> patchingDepartment(Integer deptId, DepartmentDto departmentDto) {
        Department existingDepartment = departmentRepository.findById(deptId).orElseThrow(() -> new DepartmentNotFoundException("Department Not Found!"));
        departmentMapper.updateDepartmentFromDto(departmentDto, existingDepartment);
        departmentRepository.save(existingDepartment);
        return new ResponseEntity<>("Department Patched",HttpStatus.OK);
    }

    public ResponseEntity<String> deletingDepartment(Integer deptId) {
            Department department = departmentRepository.findById(deptId).orElseThrow(() -> new DepartmentNotFoundException("Department Not Found!"));
            departmentRepository.delete(department);
            return new ResponseEntity<>("Department Deleted!",HttpStatus.OK);

    }

    public ResponseEntity<String> deletingAllDepartments() {
        departmentRepository.deleteAll();
        return new ResponseEntity<>("All Departments Deleted!", HttpStatus.OK);
    }
}
