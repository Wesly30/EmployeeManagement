package com.employee.controller;

import com.employee.dto.EmployeeDto;
import com.employee.response.APIResponse;
import com.employee.model.Employee;
import com.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee){
        return employeeService.addingEmployee(employee);
    }

    @GetMapping("getAllEmployees")
    public ResponseEntity<List<APIResponse>> getEmployees(){
        return employeeService.gettingAllEmployees();
    }

    @GetMapping("getEmployee/{empId}")
    public ResponseEntity<APIResponse> getEmployee(@PathVariable Integer empId){
        return employeeService.gettingEmployeeById(empId);
    }

    @PutMapping("updateEmployee/{empId}")
    public ResponseEntity<String> updateEmployee(@PathVariable Integer empId, @RequestBody EmployeeDto employeeDto){
        return employeeService.updatingEmployeeById(empId, employeeDto);
    }

    @PatchMapping("patchEmployee/{empId}")
    public ResponseEntity<String> patchEmployee(@PathVariable Integer empId, @RequestBody EmployeeDto employeeDto){
        return employeeService.patchingEmployeeById(empId, employeeDto);
    }

    @DeleteMapping("deleteEmployee/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer empId){
        return employeeService.deletingEmployeeById(empId);
    }

    @DeleteMapping("deleteAllEmployees")
    public ResponseEntity<String> deleteAllEmployees(){
        return employeeService.deletingAllEmployees();
    }

}

