package com.employee.feign;

import com.employee.request.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "http://localhost:8081" , value = "DEPARTMENT-SERVICE")
public interface APIClient {
    @GetMapping("departments/getDepartment/{deptId}")
    Department getDepartmentById(@PathVariable Integer deptId);

    @GetMapping("departments/getAllDepartments")
    List<Department> getAllDepartment();
}
