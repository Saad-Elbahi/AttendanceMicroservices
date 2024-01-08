package com.employeeManagement.RegistrationService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long userid;
    private String email;
    private String password;
    private String role;
}
