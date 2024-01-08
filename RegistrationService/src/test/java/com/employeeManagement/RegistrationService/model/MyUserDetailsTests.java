package com.employeeManagement.RegistrationService.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest
public class MyUserDetailsTests {
    private MyUserDetails myUserDetails;
    @Mock
    private User user;
    @BeforeEach
    void setup() throws Exception {
        myUserDetails = new MyUserDetails(user);
    }
    @Test
    void testMyUserDetailsGetters() {
       when(user.getEmail()).thenReturn("saibalmaiti23@gmail.com");
       when(user.getRole()).thenReturn("ROLE_ADMIN");
       when(user.getPassword()).thenReturn("hello");
       when(user.getUserid()).thenReturn((long)1);
       assertEquals(Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")),myUserDetails.getAuthorities());
       assertEquals("hello",myUserDetails.getPassword());
       assertEquals("saibalmaiti23@gmail.com",myUserDetails.getUsername());
       assertTrue(myUserDetails.isAccountNonExpired());
       assertTrue(myUserDetails.isAccountNonLocked());
       assertTrue(myUserDetails.isCredentialsNonExpired());
       assertTrue(myUserDetails.isEnabled());
    }
}
