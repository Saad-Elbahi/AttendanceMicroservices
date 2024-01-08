package com.employeeManagement.RegistrationService.controller;

import com.employeeManagement.RegistrationService.dto.UserDto;
import com.employeeManagement.RegistrationService.service.RegistrationService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {
    @MockBean
    private RegistrationService service;
    @Autowired
    private MockMvc mockMvc;
    private UserDto userDto;
    private Gson gson;
    private  RegistrationController controller;
    @BeforeEach
    void setup() {
        userDto = new UserDto();
        gson = new Gson();
        controller = new RegistrationController();
        controller.setRegistrationService(service);
        when(service.registerUser(userDto)).thenReturn(ResponseEntity.ok("Successfully Registered"));
        when(service.deleteUser(1)).thenReturn(ResponseEntity.ok("User Deleted Successfully"));
        when(service.updatePassword(userDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(service.getUser("saibalmaiti23@gmail.com")).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }
    @Test
    void testRegisterwithOutToken() throws Exception {
        String json = gson.toJson(userDto);
        this.mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isUnauthorized());
    }
    @Test
    void testRegister() {

        assertEquals(HttpStatus.OK,controller.registerUser(userDto).getStatusCode());
    }
    @Test
    void testDeleteUser() {
        assertEquals(HttpStatus.OK,controller.deleteUser(1).getStatusCode());
    }
    @Test
    void testGetUser() {
        assertEquals(HttpStatus.OK,controller.getUser("saibalmaiti23@gmail.com").getStatusCode());
    }
    @Test
    void testRestPassword() {
        assertEquals(HttpStatus.OK,controller.resetPassword(userDto).getStatusCode());
    }


}
