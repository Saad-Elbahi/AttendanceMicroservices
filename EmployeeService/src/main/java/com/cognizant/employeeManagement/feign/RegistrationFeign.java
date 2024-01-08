package com.cognizant.employeeManagement.feign;

import com.cognizant.employeeManagement.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "registration-service")

public interface RegistrationFeign {
    @PostMapping("/register")
    ResponseEntity<String> registerUser(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @RequestBody UserDto userDto);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteUser(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("id") long id);

    @PostMapping(value = "/authorize")
    boolean authorizeTheRequest(
            @RequestHeader(value = "Authorization", required = true) String requestTokenHeader);
}
