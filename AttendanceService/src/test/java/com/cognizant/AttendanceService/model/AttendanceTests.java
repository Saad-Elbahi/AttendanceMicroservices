package com.cognizant.AttendanceService.model;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AttendanceTests {
    @Test
    void testAttendanceBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.getFactoryCollection().addFactory(java.sql.Date.class, new SqlDateFactory());
        beanTester.getFactoryCollection().addFactory(Time.class,new SqlTimeFactory());
        beanTester.testBean(Attendance.class);
    }
    @Test
    void testAttendanceBuilder() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        Attendance attendance = Attendance.builder().id(1).userid(1).date(sqlDate).startTime(Time.valueOf("09:00:00")).endTime(Time.valueOf("19:00:00")).totalHours(10).build();
        assertEquals(1,attendance.getUserid());
    }
}
