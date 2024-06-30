package com.org.StayEase.controllers;

import com.org.StayEase.configs.JWTConfig.JWTHelper;
import com.org.StayEase.dto.LoginRequestDto;
import com.org.StayEase.dto.LoginResponseDto;
import com.org.StayEase.dto.RegistrationDto;
import com.org.StayEase.entities.User;
import com.org.StayEase.services.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserServiceImpl userService;

    // Endpoint to register new user

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok().body(userService.createUser(registrationDto));
    }

    // Endpoint to login user into the application.

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequest) throws Exception {
        this.doAuthenticate(loginRequest.getEmail(), loginRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String jwtToken = this.jwtHelper.generateToken(userDetails);

        LoginResponseDto response = new LoginResponseDto(userDetails.getUsername(), jwtToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Authentication
    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
                password);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password.");
        }
    }
}