package com.employeeManagement.RegistrationService.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserTests {
    private User user1;
    private User user2;
    @BeforeEach
    void setup() throws Exception {
        user1 = new User();
        user2 = new User(1,"saibalmaiti23@gmail.com","hello","ROLE_ADMIN");

    }
    @Test
    void testUserGetter() {
        assertEquals(1,user2.getUserid());
        assertEquals("saibalmaiti23@gmail.com", user2.getEmail());
        assertEquals("hello",user2.getPassword());
        assertEquals("ROLE_ADMIN",user2.getRole());
    }
    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(User.class);
    }
}
