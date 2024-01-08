package com.employeeManagement.RegistrationService.model;

import lombok.*;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class JwtResponse{
	private String jwttoken;
	private long userid;
	private String email;
	private String role;


}