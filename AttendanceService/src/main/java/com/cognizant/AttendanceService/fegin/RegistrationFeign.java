package com.cognizant.AttendanceService.fegin;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "registration-service")

public interface RegistrationFeign {
    @PostMapping(value = "/authorize")
    boolean authorizeTheRequest(
            @RequestHeader(value = "Authorization", required = true) String requestTokenHeader);
}
