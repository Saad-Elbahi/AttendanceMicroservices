package com.cognizant.employeeManagement.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private long userid;
    private String email;
    private String password;
    private String role;
}
