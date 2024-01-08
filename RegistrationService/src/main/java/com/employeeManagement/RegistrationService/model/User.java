package com.employeeManagement.RegistrationService.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private long userid;
    private String email;
    private String password;
    private String role;
}
