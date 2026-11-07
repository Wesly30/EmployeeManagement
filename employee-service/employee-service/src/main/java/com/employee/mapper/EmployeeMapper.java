package com.employee.mapper;

import com.employee.dto.EmployeeDto;
import com.employee.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

@BeanMapping(nullValuePropertyMappingStrategy =
        NullValuePropertyMappingStrategy.IGNORE)
void updateEmployeeFromDto(EmployeeDto employeeDto, @MappingTarget Employee existingEmployee);

}

