package com.employee.response;

import com.employee.model.Employee;
import com.employee.request.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
    private Employee employee;
    private Department department;
}
