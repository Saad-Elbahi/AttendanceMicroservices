package com.employeeManagement.RegistrationService.service;

import com.employeeManagement.RegistrationService.model.MyUserDetails;
import com.employeeManagement.RegistrationService.model.User;
import com.employeeManagement.RegistrationService.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    private User user;
    private JwtUserDetailsService jwtUserDetailsService;
    @BeforeEach
    void setup() {
        user = new User(1,"saibalmaiti23@gmail.com","hello","ROLE_ADMIN");
        jwtUserDetailsService = new JwtUserDetailsService();
    }
    @Test
    void testSuccessfulLoadUserByUserName() {
        when(userRepository.findByEmail("saibalmaiti23@gmail.com")).thenReturn(Optional.of(user));
        jwtUserDetailsService.setUserRepository(userRepository);
        assertEquals(new MyUserDetails(user),jwtUserDetailsService.loadUserByUsername("saibalmaiti23@gmail.com"));
    }
    @Test
    void testFailedLoadUserByUserName() {
        when(userRepository.findByEmail("saibalmaiti23@gmail.com")).thenReturn(Optional.empty());
        jwtUserDetailsService.setUserRepository(userRepository);
        assertThrows(UsernameNotFoundException.class,()->jwtUserDetailsService.loadUserByUsername("saibalmaiti23@gmail.com"));
    }
}
