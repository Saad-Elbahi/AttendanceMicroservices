package com.employeeManagement.RegistrationService.controller;

import com.employeeManagement.RegistrationService.dto.UserDto;
import com.employeeManagement.RegistrationService.model.User;
import com.employeeManagement.RegistrationService.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class RegistrationController {
    private RegistrationService registrationService;
    @Autowired
    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        return registrationService.registerUser(userDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        return registrationService.deleteUser(id);
    }
    @GetMapping("getuser/{email}")
    public ResponseEntity<?> getUser(@PathVariable("email") String email) {
        return registrationService.getUser(email);
    }
    @PutMapping("/resetpassword")
    public ResponseEntity<?> resetPassword(@RequestBody UserDto userDto) {
        return registrationService.updatePassword(userDto);
    }
}
