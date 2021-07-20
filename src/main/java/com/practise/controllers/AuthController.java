package com.practise.controllers;

import com.practise.dto.AuthenticationResponse;
import com.practise.dto.LoginRequest;
import com.practise.dto.RegisterRequest;
import com.practise.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
    authService.register(registerRequest);
    return ResponseEntity.status(CREATED).body("Created account");
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.status(CREATED).body(authService.login(loginRequest));
  }
}
