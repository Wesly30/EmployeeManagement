package com.employee.servicetest;

import com.employee.dto.EmployeeDto;
import com.employee.feign.APIClient;
import com.employee.mapper.EmployeeMapper;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.request.Department;
import com.employee.response.APIResponse;
import com.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeServiceTest {


    @Mock
    APIClient apiClient;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    EmployeeMapper employeeMapper;

    @InjectMocks
    EmployeeService employeeService;

    Employee employee;
    EmployeeDto employeeDto;
    Department departmentDto;

    @BeforeEach
    public void setUp() throws ParseException {

        Date now = new Date();

        employee = new Employee();
        employee.setEmpId(1);
        employee.setEmpName("Don");
        employee.setEmpMail("don@org.com");
        employee.setSalary(21000.00);
        employee.setDeptId(1);
        employee.setCreatedAt(now);
        employee.setUpdatedAt(null);

        employeeDto  = new EmployeeDto();
        employeeDto.setEmpId(1);
        employeeDto.setEmpName("Don");
        employeeDto.setEmpMail("don@org.com");
        employeeDto.setSalary(21000.00);
        employeeDto.setDeptId(1);
        employeeDto.setCreatedAt(now);
        employeeDto.setUpdatedAt(null);

        departmentDto = new Department();
        departmentDto.setDeptId(1);
        departmentDto.setDeptName("IT Development");
        departmentDto.setDeptCode("IT101");
    }

    @Test
    public void testAddingEmployee(){

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        ResponseEntity<String> response = employeeService.addingEmployee(employee);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Employee Added", response.getBody());
        assertNotNull(employee.getCreatedAt());
    }

    @Test
    public void testGettingAllEmployees(){

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        when(apiClient.getAllDepartment()).thenReturn(Arrays.asList(departmentDto));

        ResponseEntity<List<APIResponse>> response = employeeService.gettingAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(employee, response.getBody().get(0).getEmployee());
        assertEquals(departmentDto, response.getBody().get(0).getDepartment());

    }

    @Test
    public void testGettingEmployeeById(){
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        when(apiClient.getDepartmentById(anyInt())).thenReturn(departmentDto);

        ResponseEntity<APIResponse> response = employeeService.gettingEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody().getEmployee());
        assertEquals(departmentDto, response.getBody().getDepartment());
    }

    @Test
    public void testUpdatingEmployeeById(){

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        doNothing().when(employeeMapper).updateEmployeeFromDto(any(EmployeeDto.class), any(Employee.class));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        ResponseEntity<String> response = employeeService.updatingEmployeeById(1, employeeDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee Details Updated", response.getBody());
        assertNotNull(employee.getUpdatedAt());
    }

    @Test
    public void testPatchingEmployeeById(){

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        doNothing().when(employeeMapper).updateEmployeeFromDto(any(EmployeeDto.class), any(Employee.class));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        ResponseEntity<String> response = employeeService.patchingEmployeeById(1, employeeDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee Details Patched", response.getBody());
    }

    @Test
    public void testDeletingEmployeeById(){

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        ResponseEntity<String> response = employeeService.deletingEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee Deleted!", response.getBody());

    }

    @Test
    public void testDeleteAllEmployees(){

        doNothing().when(employeeRepository).deleteAll();

        ResponseEntity<String> response = employeeService.deletingAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All Employees Deleted!", response.getBody());

    }
}
