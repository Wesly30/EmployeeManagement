package com.department.controller;


import com.department.dto.DepartmentDto;
import com.department.model.Department;
import com.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("addDepartment")
    public ResponseEntity<String> addDepartment(@RequestBody Department department){
        return departmentService.addingDepartment(department);
    }

    @GetMapping("getDepartment/{deptId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Integer deptId)  {
        return departmentService.gettingDepartmentById(deptId);
    }

    @GetMapping("getAllDepartments")
    public ResponseEntity<List<Department>> getAllDepartment(){
        return departmentService.gettingAllDepartments();
    }

    @PutMapping("updateDepartment/{deptId}")
    public ResponseEntity<String> updateDepartment(@PathVariable Integer deptId, @RequestBody DepartmentDto departmentDto){
        return departmentService.updatingDepartment(deptId, departmentDto);
    }

    @PatchMapping("patchDepartment/{deptId}")
    public ResponseEntity<String> patchDepartment(@PathVariable Integer deptId, @RequestBody DepartmentDto departmentDto){
        return departmentService.patchingDepartment(deptId, departmentDto);
    }

    @DeleteMapping("deleteDepartment/{deptId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Integer deptId){
        return departmentService.deletingDepartment(deptId);
    }

    @DeleteMapping("deleteAllDepartments")
    public ResponseEntity<String> deleteAllDepartment(){
        return departmentService.deletingAllDepartments();
    }

}
