package com.employeeManagement.RegistrationService.controller;

import com.employeeManagement.RegistrationService.config.JwtTokenUtil;
import com.employeeManagement.RegistrationService.exception.AuthorizationException;
import com.employeeManagement.RegistrationService.model.JwtRequest;
import com.employeeManagement.RegistrationService.model.MyUserDetails;
import com.employeeManagement.RegistrationService.model.User;
import com.employeeManagement.RegistrationService.service.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUserDetailsService userDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void textExistingUserAuthorize() throws Exception {
        User user = new User(1, "saibalmaiti23@gmail.com", "hello","ROLE_ADMIN");
        UserDetails details = new MyUserDetails(user);
        when(userDetailsService.loadUserByUsername("saibalmaiti23@gmail.com")).thenReturn((MyUserDetails) details);
        when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("admin");
        mockMvc.perform(MockMvcRequestBuilders.post("/authorize").header("Authorization", "Bearer token"))
                .andExpect(status().isOk());

    }

    @Test
    void textNullTokenAuthorize() throws Exception {
        User user = new User(1, "saibalmaiti23@gmail.com", "hello","ROLE_ADMIN");
        UserDetails details = new MyUserDetails(user);
        when(userDetailsService.loadUserByUsername("admin")).thenReturn((MyUserDetails) details);
        when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("admin");
        mockMvc.perform(MockMvcRequestBuilders.post("/authorize").header("Authorization", "")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
}
