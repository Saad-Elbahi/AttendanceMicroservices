package com.employeeManagement.RegistrationService.service;

import com.employeeManagement.RegistrationService.dto.UserResponse;
import com.employeeManagement.RegistrationService.repository.UserRepository;
import com.employeeManagement.RegistrationService.dto.UserDto;
import com.employeeManagement.RegistrationService.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class RegistrationService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    static final String INVALID_USER = "Invalid User Id";

    public ResponseEntity<String> registerUser(UserDto userDto) {
        boolean userExists = userRepository.findByEmail(userDto.getEmail()).isPresent();
        if(userExists)
            return ResponseEntity.status(403).body("User Already Exists");
        User user = new User();
        //Mapping Data from model to entity
        user.setUserid(userDto.getUserid());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        //Encode the password using bCrypt Encoder.
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        //Generate token for email verification
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("saibalmaiti0@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Employee Login Information");
        message.setText("Username: "+user.getEmail()+"\n Password: "+userDto.getPassword()+"\n This is a general password, please reset it after first login for security.");
        try {
            emailService.sendEmail(message);
            return ResponseEntity.ok("Successfully Registered");
        }
        catch (MailSendException e) {
            userRepository.delete(user);
            return ResponseEntity.status(403).body("Failed to send email");
        }
    }
    public ResponseEntity<?> updatePassword(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByUserid(userDto.getUserid());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            Map<String, String> response = new HashMap<>();
            response.put("response", "Password Updated Successfully");
            return ResponseEntity.ok().body(response);
        }
        else {
            Map<String, String> response = new HashMap<>();
            response.put("response", INVALID_USER);
            return ResponseEntity.status(403).body(response);
        }
    }

    public ResponseEntity<String> deleteUser(long id) {
        if(userRepository.findByUserid(id).isPresent()) {
            userRepository.deleteByUserid(id);
            return ResponseEntity.ok("User Deleted Successfully");
        }
        else {
            return ResponseEntity.status(403).body(INVALID_USER);
        }
    }

    public ResponseEntity<?> getUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        }
        else
            return ResponseEntity.status(403).body(INVALID_USER);
    }
}
