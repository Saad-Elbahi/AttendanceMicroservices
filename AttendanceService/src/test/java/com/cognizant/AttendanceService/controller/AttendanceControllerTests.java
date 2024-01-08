package com.cognizant.AttendanceService.controller;

import com.cognizant.AttendanceService.dto.GiveAttendanceDto;
import com.cognizant.AttendanceService.fegin.RegistrationFeign;
import com.cognizant.AttendanceService.service.AttendanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AttendanceControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AttendanceService attendanceService;
    @MockBean
    private RegistrationFeign registrationFeign;
    private String token;
    private String err_token;
    private GiveAttendanceDto giveAttendanceDto;
    @BeforeEach
    void setup() {
        token = "Bearer test_token";
        err_token = "error_token";
        giveAttendanceDto = new GiveAttendanceDto(1,"09:00","19:00");
        when(attendanceService.giveAttendance(giveAttendanceDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(attendanceService.getWeeklyAttendance(1)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(registrationFeign.authorizeTheRequest(token)).thenReturn(true);
        when(registrationFeign.authorizeTheRequest(err_token)).thenReturn(false);
    }
    @Test
    void testSuccessfulGiveAttendance() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/giveattendance").header("Authorization",token).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(giveAttendanceDto)))
                .andExpect(status().isOk());
    }
    @Test
    void testFailedGiveAttendance() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/giveattendance").header("Authorization",err_token).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(giveAttendanceDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testSuccessfulGetAttendance() throws Exception {
        mockMvc.perform(get("/getweeklyattendance/1").header("Authorization",token)).andExpect(status().isOk());
    }
    @Test
    void testFailedGetAttendace() throws Exception {
        mockMvc.perform(get("/getweeklyattendance/1").header("Authorization",err_token)).andExpect(status().isBadRequest());
    }
}
