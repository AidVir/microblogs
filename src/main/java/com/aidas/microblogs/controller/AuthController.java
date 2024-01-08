package com.aidas.microblogs.controller;

import com.aidas.microblogs.dto.UserCredentialsDto;
import com.aidas.microblogs.dto.MessageResponse;

import com.aidas.microblogs.exception.ValidationException;
import com.aidas.microblogs.service.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private UserService userService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserCredentialsDto loginRequest) {
    return ResponseEntity.ok(userService.authenticateUser(loginRequest));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserCredentialsDto signUpRequest) {
    try {
      userService.registerUser(signUpRequest);
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    } catch (ValidationException e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }
}

