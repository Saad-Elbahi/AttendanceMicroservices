package com.employeeManagement.RegistrationService.service;

import com.employeeManagement.RegistrationService.dto.UserDto;
import com.employeeManagement.RegistrationService.model.User;
import com.employeeManagement.RegistrationService.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.when;


@SpringBootTest
public class RegistrationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailService emailService;
    private RegistrationService registrationService;
    private UserDto userDto;
    @BeforeEach
    void setup() {
        registrationService = new RegistrationService();
        userDto = new UserDto(1,"saibalmaiti23@gmail.com","hello","ROLE_ADMIN");
    }
    @Test
    void testSuccessfulRegistration() {
        when(userRepository.findByEmail("saibalmaiti23@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("hello")).thenReturn("hello");
        registrationService.setUserRepository(userRepository);
        registrationService.setPasswordEncoder(passwordEncoder);
        registrationService.setEmailService(emailService);
        assertEquals(ResponseEntity.ok("Successfully Registered"),registrationService.registerUser(userDto));
    }
    @Test
    void testFailedRegistrationOnPresentUser() {
        User user = new User(1,"saibalmaiti23@gmail.com","hello","ROLE_ADMIN");
        when(userRepository.findByEmail("saibalmaiti23@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("hello")).thenReturn("hello");
        registrationService.setUserRepository(userRepository);
        registrationService.setPasswordEncoder(passwordEncoder);
        registrationService.setEmailService(emailService);
        assertEquals(ResponseEntity.status(403).body("User Already Exists"),registrationService.registerUser(userDto));
    }
    @Test
    void testFailedRegistrationOnFailedEmail() {
        when(userRepository.findByEmail("saibalmaiti23@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("hello")).thenReturn("hello");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("saibalmaiti0@gmail.com");
        simpleMailMessage.setTo("saibalmaiti23@gmail.com");
        simpleMailMessage.setSubject("Employee Login Information");
        simpleMailMessage.setText("Username: "+"saibalmaiti23@gmail.com"+"\n Password: "+"hello"+"\n This is a general password, please reset it after first login for security.");
        willThrow(new MailSendException("Error Message")).given(emailService).sendEmail(simpleMailMessage);
        registrationService.setUserRepository(userRepository);
        registrationService.setPasswordEncoder(passwordEncoder);
        registrationService.setEmailService(emailService);
        assertEquals(ResponseEntity.status(403).body("Failed to send email"),registrationService.registerUser(userDto));
    }
    @Test
    void testSuccessfulPasswordReset() {
        User user = new User(1,"saibalmaiti23@gmail.com","hello","ROLE_ADMIN");
        when(userRepository.findByUserid((long)1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("hello")).thenReturn("hello");
        registrationService.setUserRepository(userRepository);
        registrationService.setPasswordEncoder(passwordEncoder);
        Map<String, String> response = new HashMap<>();
        response.put("response", "Password Updated Successfully");
        assertEquals(ResponseEntity.ok().body(response),registrationService.updatePassword(userDto));
    }
    @Test
    void testFailedPasswordReset() {
        when(userRepository.findByUserid((long)1)).thenReturn(Optional.empty());
        registrationService.setUserRepository(userRepository);
        Map<String, String> response = new HashMap<>();
        response.put("response", "Invalid User Id");
        assertEquals(ResponseEntity.status(403).body(response),registrationService.updatePassword(userDto));
    }
    @Test
    void testSuccessfulDeleteUser() {
        User user = new User(1,"saibalmaiti23@gmail.com","hello","ROLE_ADMIN");
        when(userRepository.findByUserid((long)1)).thenReturn(Optional.of(user));
        registrationService.setUserRepository(userRepository);
        assertEquals(ResponseEntity.ok("User Deleted Successfully"),registrationService.deleteUser(1));
    }
    @Test
    void testFailedDeleteUser() {
        when(userRepository.findByUserid((long)1)).thenReturn(Optional.empty());
        registrationService.setUserRepository(userRepository);
        assertEquals(ResponseEntity.status(403).body("Invalid User Id"),registrationService.deleteUser(1));
    }
    @Test
    void testSuccessfulGetUser() {
        User user = new User(1,"saibalmaiti23@gmail.com","hello","ROLE_ADMIN");
        when(userRepository.findByEmail("saibalmaiti23@gmail.com")).thenReturn(Optional.of(user));
        registrationService.setUserRepository(userRepository);
        assertEquals(ResponseEntity.ok(user),registrationService.getUser("saibalmaiti23@gmail.com"));
    }
    void testFailedGetUser() {
        when(userRepository.findByEmail("saibalmaiti23@gmail.com")).thenReturn(Optional.empty());
        registrationService.setUserRepository(userRepository);
        assertEquals(ResponseEntity.status(403).body("Invalid User Id"),registrationService.getUser("saibalmaiti23@gmail.com"));
    }
}
