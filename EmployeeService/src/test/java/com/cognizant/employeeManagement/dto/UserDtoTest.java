package com.cognizant.employeeManagement.dto;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserDtoTest {
    @Test
    void testUserDtoBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(UserDto.class);
    }
    @Test
    void testUserDtoBuilder() {
        UserDto userDto = UserDto.builder().userid(1).email("test@email.com").password("test").role("ROLE_TEST").build();
        assertEquals(1,userDto.getUserid());
        assertEquals("test@email.com",userDto.getEmail());
        assertEquals("test",userDto.getPassword());
        assertEquals("ROLE_TEST",userDto.getRole());
    }
}
