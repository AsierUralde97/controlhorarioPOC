package com.iberdrola.controlhorario.domain;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Workday {

    private String employeeNumber;
    private Timestamp entryTime;
    private Timestamp exitTime;
}
