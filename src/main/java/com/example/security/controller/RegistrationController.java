package com.example.security.controller;

import com.example.security.exception.exception.MissingParameterException;
import com.example.security.model.register.RegistrationRequestDTO;
import com.example.security.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RegistrationController {

  //TODO return dto

  private final RegistrationService registrationService;

  @Autowired
  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  //TODO return DTO
  @PostMapping("/register")
  public ResponseEntity<String> registerUser(
      @RequestBody(required = false) RegistrationRequestDTO request) throws MissingParameterException {
    return ResponseEntity.ok(registrationService.register(request));
  }


  //TODO return DTO
  @PostMapping("/register")
  public ResponseEntity<String> validateUser(
      @RequestParam("token") String token) {

    return ResponseEntity.ok(registrationService.confirmToken(token));
  }


}
