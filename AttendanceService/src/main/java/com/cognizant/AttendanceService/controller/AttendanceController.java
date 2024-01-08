package com.cognizant.AttendanceService.controller;

import com.cognizant.AttendanceService.dto.GiveAttendanceDto;
import com.cognizant.AttendanceService.exception.AuthorizationException;
import com.cognizant.AttendanceService.fegin.RegistrationFeign;
import com.cognizant.AttendanceService.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private RegistrationFeign registrationFeign;
    @PostMapping("/giveattendance")
    public ResponseEntity<Map> giveAttendance(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @RequestBody GiveAttendanceDto giveAttendanceDto) throws AuthorizationException {
        if(registrationFeign.authorizeTheRequest(requestTokenHeader)) {
            log.info("----DTO Object Data Received-------");
            log.info(giveAttendanceDto.toString());
            return attendanceService.giveAttendance(giveAttendanceDto);
        }
        else
            throw new AuthorizationException("Invalid JWT Token");

    }
    @GetMapping("/getweeklyattendance/{userid}")
    public ResponseEntity<List> getWeeklyAttendance(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("userid") long userid) throws AuthorizationException {
        if(registrationFeign.authorizeTheRequest(requestTokenHeader)) {
            return attendanceService.getWeeklyAttendance(userid);
        }
        else
            throw new AuthorizationException("Invalid JWT Token");
    }
}
