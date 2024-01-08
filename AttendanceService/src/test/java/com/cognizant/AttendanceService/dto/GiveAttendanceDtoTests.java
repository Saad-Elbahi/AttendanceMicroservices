package com.cognizant.AttendanceService.dto;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GiveAttendanceDtoTests {
    @Test
    void testGiveAttendanceDtoBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(GiveAttendanceDto.class);
    }
    @Test
    void testGiveAttendanceDtoAllargsConst() {
        GiveAttendanceDto giveAttendanceDto = new GiveAttendanceDto(1,"09:00:00","19:00:00");
        assertEquals(1,giveAttendanceDto.getUserid());
        assertEquals("09:00:00",giveAttendanceDto.getStartTime());
    }
}
