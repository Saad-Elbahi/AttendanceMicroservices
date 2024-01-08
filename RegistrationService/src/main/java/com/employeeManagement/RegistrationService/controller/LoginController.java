package com.employeeManagement.RegistrationService.controller;

import com.employeeManagement.RegistrationService.config.JwtTokenUtil;
import com.employeeManagement.RegistrationService.exception.AuthorizationException;
import com.employeeManagement.RegistrationService.model.JwtRequest;
import com.employeeManagement.RegistrationService.model.JwtResponse;
import com.employeeManagement.RegistrationService.model.MyUserDetails;
import com.employeeManagement.RegistrationService.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;


    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws AuthorizationException {
        log.info(authenticationRequest.getUserName(), authenticationRequest.getPassword());
        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());
        final MyUserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token,userDetails.getUser().getUserid(),userDetails.getUser().getEmail(),userDetails.getUser().getRole()));
    }

    private Authentication authenticate(String userName, String password) throws AuthorizationException {
        try {
            log.info("Inside authenticate Method==================================");
            Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            log.info("Authentication Successful.....");
            return auth;

        } catch (DisabledException e) {
            throw new AuthorizationException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new AuthorizationException("INVALID_CREDENTIALS");
        }

    }

    @PostMapping(value = "/authorize")
    public boolean authorizeTheRequest(
            @RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
        log.info("Inside authorize =============="+requestTokenHeader);
        String jwtToken = null;
        String userName = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            log.info("JWT Tocken ======================="+jwtToken);
            try {
                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException | ExpiredJwtException e) {
                return false;
            }
        }
        return userName != null;

    }

}
