package com.employee.service;

import com.employee.dto.EmployeeDto;
import com.employee.mapper.EmployeeMapper;
import com.employee.request.Department;
import com.employee.model.Employee;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.feign.APIClient;
import com.employee.response.APIResponse;
import com.employee.repository.EmployeeRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    APIClient apiClient;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

   @Transactional
    public ResponseEntity<String> addingEmployee(Employee employee) {
        employeeRepository.save(employee);
        employee.setCreatedAt(new Date());
        return new ResponseEntity<>("Employee Added", HttpStatus.CREATED);
    }

    public ResponseEntity<List<APIResponse>> gettingAllEmployees() {
        try {
            List<Employee> employeesList = employeeRepository.findAll();
            List<Department> departmentList = apiClient.getAllDepartment();

            Map<Integer, Department> departmentMap = departmentList.stream()
                    .collect(Collectors.toMap(Department::getDeptId, departmentDto -> departmentDto));

            List<APIResponse> apiResponseList = employeesList.stream().map(employee -> new APIResponse(employee, departmentMap.get(employee.getDeptId()))).collect(Collectors.toList());
            return new ResponseEntity<>(apiResponseList, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    //@Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    public ResponseEntity<APIResponse> gettingEmployeeById(Integer empId) {

       LOGGER.info("Inside getEmployeeById() Method");

        Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found!"));
        Department department = apiClient.getDepartmentById(employee.getDeptId());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setEmployee(employee);
        apiResponse.setDepartment(department);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updatingEmployeeById(Integer empId, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found!"));

        System.out.println("Before Update");
        System.out.println(employeeDto);
        System.out.println(existingEmployee);

        employeeMapper.updateEmployeeFromDto(employeeDto, existingEmployee);

        System.out.println("After Update");
        System.out.println(employeeDto);
        System.out.println(existingEmployee);

        existingEmployee.setUpdatedAt(new Date());
        employeeRepository.save(existingEmployee);
        return new ResponseEntity<>("Employee Details Updated", HttpStatus.OK);
    }

    public ResponseEntity<String> patchingEmployeeById(Integer empId, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found!"));
        employeeMapper.updateEmployeeFromDto(employeeDto, existingEmployee);
        employeeRepository.save(existingEmployee);
        return new ResponseEntity<>("Employee Details Patched", HttpStatus.OK);
    }

    public ResponseEntity<String> deletingEmployeeById(Integer empId) {
        Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found!"));
        employeeRepository.delete(employee);
        return new ResponseEntity<>("Employee Deleted!", HttpStatus.OK);
    }

    public ResponseEntity<String> deletingAllEmployees() {
        employeeRepository.deleteAll();
        return new ResponseEntity<>("All Employees Deleted!", HttpStatus.OK);
    }

    //FALLBACK METHOD
    public ResponseEntity<APIResponse> getDefaultDepartment(Integer empId, Exception exception){

        LOGGER.info("Inside getDefaultDepartment() Method");
        Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found!"));
        Department department = new Department();
        department.setDeptCode("D000");
        department.setDeptName("Department Name ");

        APIResponse apiResponse = new APIResponse();
        apiResponse.setEmployee(employee);
        apiResponse.setDepartment(department);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
}


