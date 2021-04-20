package com.example.security.controller;

import com.example.security.exception.exception.UserException;
import com.example.security.model.login.UserLoginDTO;
import com.example.security.security.Jwt.AuthenticationRequest;
import com.example.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

  private final LoginService loginService;

  @Autowired
  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/login")
  public ResponseEntity<UserLoginDTO> login(@RequestBody(required = false) AuthenticationRequest loginRequest)
      throws UserException {
    return ResponseEntity.ok(loginService.loginUser(loginRequest));
  }

}
