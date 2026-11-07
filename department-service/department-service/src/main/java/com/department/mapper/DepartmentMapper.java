package com.department.mapper;


import com.department.dto.DepartmentDto;
import com.department.model.Department;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    void updateDepartmentFromDto(DepartmentDto departmentDto, @MappingTarget Department existingDepartment);
}
