package com.cognizant.AttendanceService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GiveAttendanceDto {
    private long userid;
    private String startTime;
    private String endTime;
}
