package com.employee.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDto {
    private Integer empId;
    private String empName;
    private String empMail;
    private Double salary;
    private Integer deptId;
    private Date createdAt;
    private Date updatedAt;
}
