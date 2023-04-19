package com.iberdrola.controlhorario.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iberdrola.controlhorario.domain.Workday;
import com.iberdrola.controlhorario.mappers.WorkdayMapper;

@Service
public class MainServices {

    @Autowired
    WorkdayMapper workdayMapper;

    // Insert workday
    public void insertWorkdayInDatabase(String employeeNumber) {

        int size = workdayMapper.obtainCurrentWorkday(employeeNumber).size();
        List<Workday> currentWorkdayEntries = workdayMapper.obtainCurrentWorkday(employeeNumber);

        if (!currentWorkdayEntries.isEmpty()) {

            if (currentWorkdayEntries.get(size - 1).getExitTime() == null) {

                workdayMapper.updateWorkday(currentWorkdayEntries.get(size - 1));

            } else {

                workdayMapper.insertWorkday(employeeNumber);

            }

        } else {

            workdayMapper.insertWorkday(employeeNumber);
        }

    }

    // Obtain workdays
    public List<Workday> obtainWorkdayCurrentDate(String employeeNumber) {

        return workdayMapper.obtainCurrentWorkday(employeeNumber);
    }

    public List<Workday> obtainWorkdayByDate(String employeeNumber, String date) {
        return workdayMapper.obtainWorkdayByDate(employeeNumber, date);
    }

    public List<Workday> obtainWorkdayLastMonth(String employeeNumber) {
        System.out.println(workdayMapper.obtainWorkdayLastMonth(employeeNumber));
        return workdayMapper.obtainWorkdayLastMonth(employeeNumber);

    }

    public List<Workday> obtainWorkdayLastYear(String employeeNumber) {
        return workdayMapper.obtainWorkdayLastYear(employeeNumber);
    }

    // Calculate total working hours
    public String totalWorkingHoursCurrentDate(String employeeNumber) {
        return calculateTotalWorkingHour(0, employeeNumber, null);
    }

    public String totalWorkingHoursByDate(String employeeNumber, String date) {
        return calculateTotalWorkingHour(1, employeeNumber, date);
    }

    public String totalWorkingHoursLastMonth(String employeeNumber) {

        return calculateTotalWorkingHour(2, employeeNumber, null);
    }

    public String totalWorkingHoursLastYear(String employeeNumber) {
        return calculateTotalWorkingHour(3, employeeNumber, null);
    }

    public String calculateTotalWorkingHour(int method, String employeeNumber, String date) {
        int totalHours = 0;
        List<Workday> workdayList = null;

        switch (method) {
            case 0: // current workday
                workdayList = workdayMapper.obtainCurrentWorkday(employeeNumber);
                break;
            case 1: // workday by date
                workdayList = workdayMapper.obtainWorkdayByDate(employeeNumber, date);
                break;
            case 2: // workday by month
                workdayList = workdayMapper.obtainWorkdayLastMonth(employeeNumber);
                break;
            case 3: // workday by year
                workdayList = workdayMapper.obtainWorkdayLastYear(employeeNumber);
                break;
            default:
                break;
        }
        for (Workday workday : workdayList) {

            if (workday.getExitTime() != null) {

                totalHours += (workday.getExitTime().getTime() -
                        workday.getEntryTime().getTime());

            }

        }
        long HH = TimeUnit.MILLISECONDS.toHours(totalHours);
        long MM = TimeUnit.MILLISECONDS.toMinutes(totalHours) % 60;
        long SS = TimeUnit.MILLISECONDS.toSeconds(totalHours) % 60;

        String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);

        return timeInHHMMSS;
    }

}
