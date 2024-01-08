package com.cognizant.AttendanceService.service;

import com.cognizant.AttendanceService.dao.AttendanceDao;
import com.cognizant.AttendanceService.dto.GiveAttendanceDto;
import com.cognizant.AttendanceService.model.Attendance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AttendanceServiceTests {
    @Mock
    private AttendanceDao attendanceDao;
    @InjectMocks
    private AttendanceService service;
    private Attendance att1, att2;
    private List<Attendance> attendanceList;
    private java.sql.Date sqlDate1;
    @BeforeEach
    void setup() {
        Date date = new Date();
        sqlDate1 = new java.sql.Date(date.getTime());
        Date prevDate = new Date(date.getTime() - (1000 * 60 * 60 * 24));
        java.sql.Date sqlDate2 = new java.sql.Date(prevDate.getTime());
        att1 = Attendance.builder().id(2).userid(1).date(sqlDate1).startTime(Time.valueOf("09:00:00")).endTime(Time.valueOf("19:00:00")).totalHours(10).build();
        att2 = Attendance.builder().id(1).userid(1).date(sqlDate2).startTime(Time.valueOf("09:00:00")).endTime(Time.valueOf("19:00:00")).totalHours(10).build();
        attendanceList = new ArrayList<>();
        attendanceList.add(att1);
        attendanceList.add(att2);
        when(attendanceDao.getAttendanceByDateByWeek(sqlDate1,1)).thenReturn(attendanceList);

    }
    @Test
    void testSuccessfulAttendance() {
        when(attendanceDao.findAttendanceByDate(sqlDate1)).thenReturn(Optional.empty());
        GiveAttendanceDto giveAttendanceDto = new GiveAttendanceDto(1,"09:00:00","19:00:00");
        assertEquals(200,service.giveAttendance(giveAttendanceDto).getStatusCodeValue());
    }
    @Test
    void testFailedAttendanceForSameDate() {
        when(attendanceDao.findAttendanceByDate(any(java.sql.Date.class))).thenReturn(Optional.of(att1));
        GiveAttendanceDto giveAttendanceDto = new GiveAttendanceDto(1,"09:00:00","19:00:00");
        assertEquals(403,service.giveAttendance(giveAttendanceDto).getStatusCodeValue());
    }
    @Test
    void testFailedAttendanceForException() {
        when(attendanceDao.findAttendanceByDate(sqlDate1)).thenReturn(Optional.empty());
        GiveAttendanceDto giveAttendanceDto = new GiveAttendanceDto(1,"09:00","19:00");
        assertEquals(403,service.giveAttendance(giveAttendanceDto).getStatusCodeValue());
    }
    @Test
    void testGetAttendance() {
        assertEquals(200,service.getWeeklyAttendance(1).getStatusCodeValue());
    }
}
