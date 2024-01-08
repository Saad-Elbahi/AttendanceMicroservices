package com.cognizant.AttendanceService.service;

import com.cognizant.AttendanceService.dao.AttendanceDao;
import com.cognizant.AttendanceService.dto.GiveAttendanceDto;
import com.cognizant.AttendanceService.model.Attendance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class AttendanceService {
    private AttendanceDao attendanceDao;
    @Autowired
    public void setAttendanceDao(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }
    public ResponseEntity<Map> giveAttendance(GiveAttendanceDto giveAttendanceDto) {
        try {
            Attendance attendance = new Attendance();
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            if(attendanceDao.findAttendanceByDate(sqlDate).isPresent()) {
                log.info("---- Attendance for day is present-----");
                Map<String, String> response = new HashMap<>();
                response.put("response", "Attendance Already Given");
                return ResponseEntity.status(403).body(response);
            }
            attendance.setUserid(giveAttendanceDto.getUserid());
            attendance.setDate(sqlDate);
            attendance.setStartTime(Time.valueOf(giveAttendanceDto.getStartTime()));
            attendance.setEndTime(Time.valueOf(giveAttendanceDto.getEndTime()));
            LocalTime startTime = attendance.getStartTime().toLocalTime();
            LocalTime endTime = attendance.getEndTime().toLocalTime();
            long totalHour = Math.abs(startTime.until(endTime, ChronoUnit.HOURS));
            attendance.setTotalHours(totalHour);
            log.info("-----Attendance Object------");
            log.info(attendance.toString());
            attendanceDao.save(attendance);
            List<Attendance> attendanceList = attendanceDao.getAttendanceByDateByWeek(sqlDate,giveAttendanceDto.getUserid());
            log.info("Day of week: "+day);
            long totalWeeklyHour = 0;
            for(Attendance attendance1: attendanceList) {
                totalWeeklyHour += attendance1.getTotalHours();
            }
            long avgWorkHour = totalWeeklyHour / day;
            log.info("Avg Work Hour: "+avgWorkHour);
            Map<String, String> response = new HashMap<>();
            response.put("response", "Attendance Added Successfully");
            return ResponseEntity.ok().body(response);
        }
        catch (Exception e) {
            log.info(Arrays.toString(e.getStackTrace()));
            Map<String, String> response = new HashMap<>();
            response.put("response", "Error While Giving Attendance");
            return ResponseEntity.status(403).body(response);
        }
    }
    public ResponseEntity<List> getWeeklyAttendance(long userid) {
        Long[] data = new Long[7];
        Arrays.fill(data,Long.valueOf(0));
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        List<Attendance> attendanceList = attendanceDao.getAttendanceByDateByWeek(sqlDate,userid);
        for(Attendance attendance1: attendanceList) {
            Date d = attendance1.getDate();
            calendar.setTime(d);
            int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
//            log.info("Day:"+day);
            data[day] = attendance1.getTotalHours();
        }
        List<Long> listData = Arrays.asList(data);
        return ResponseEntity.ok().body(listData);
    }
}
