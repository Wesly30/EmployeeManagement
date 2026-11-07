package com.department.servicetest;

import com.department.dto.DepartmentDto;
import com.department.mapper.DepartmentMapper;
import com.department.model.Department;
import com.department.repository.DepartmentRepository;
import com.department.service.DepartmentService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DepartmentServiceTest {

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    DepartmentMapper departmentMapper;

    @InjectMocks
    DepartmentService departmentService;

    Department department;
    DepartmentDto departmentDto;

    @BeforeEach
    public void setUp(){

        department = new Department();
        department.setDeptId(1);
        department.setDeptCode("IT101");
        department.setDeptName("IT Development");

        departmentDto = new DepartmentDto();
        departmentDto.setDeptId(1);
        departmentDto.setDeptCode("IT101");
        departmentDto.setDeptName("IT Development");

    }

    @Test
    public void testAddingDepartment(){

        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        ResponseEntity<String> response = departmentService.addingDepartment(department);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Department Added!", response.getBody());
    }

    @Test
    public void testGettingDepartmentById(){

        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(department));

        ResponseEntity<Department> response = departmentService.gettingDepartmentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department, response.getBody());

    }

    @Test
    public void testGettingAllDepartments(){

        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department));

        ResponseEntity<List<Department>> response = departmentService.gettingAllDepartments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department, response.getBody().get(0));
        assertFalse(response.getBody().isEmpty());

    }

    @Test
    public void testUpdatingDepartment(){

        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(department));
        doNothing().when(departmentMapper).updateDepartmentFromDto(any(DepartmentDto.class), any(Department.class));

        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        ResponseEntity<String> response = departmentService.updatingDepartment(1, departmentDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Department Updated", response.getBody());


    }

    @Test
    public void testPatchingDepartment(){
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(department));
        doNothing().when(departmentMapper).updateDepartmentFromDto(any(DepartmentDto.class), any(Department.class));

        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        ResponseEntity<String> response = departmentService.patchingDepartment(1, departmentDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Department Patched", response.getBody());

    }

    @Test
    public void testDeletingDepartment(){

        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).delete(any(Department.class));

        ResponseEntity<String> response = departmentService.deletingDepartment(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Department Deleted!", response.getBody());

    }

    @Test
    public void testDeletingAllDepartments(){
        doNothing().when(departmentRepository).deleteAll();

        ResponseEntity<String> response = departmentService.deletingAllDepartments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All Departments Deleted!", response.getBody());

    }

}
