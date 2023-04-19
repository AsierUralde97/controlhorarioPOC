package com.iberdrola.controlhorario.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.iberdrola.controlhorario.domain.Workday;

@Mapper
public interface WorkdayMapper {

    public void insertWorkday(@Param("employeeNumber") String employeeNumber);

    public void updateWorkday(@Param("workday") Workday workday);

    public List<Workday> obtainCurrentWorkday(@Param("employeeNumber") String employeeNumber);

    public List<Workday> obtainWorkdayByDate(@Param("employeeNumber") String employeeNumber,
            @Param("date") String date);

    public List<Workday> obtainWorkdayLastMonth(@Param("employeeNumber") String employeeNumber);

    public List<Workday> obtainWorkdayLastYear(@Param("employeeNumber") String employeeNumber);
}
