package com.example.security.service;

import com.example.security.exception.exception.MissingParameterException;
import com.example.security.exception.exception.UserException;
import com.example.security.model.login.UserLoginDTO;
import com.example.security.security.Jwt.AuthenticationRequest;
import com.example.security.security.Jwt.AuthenticationResponse;
import com.example.security.security.Jwt.JwtUtil;
import com.example.security.security.Jwt.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LoginService {


  private final UserService userService;
  private final JwtUtil jwtTokenUtil;
  private final MyUserDetailsService userDetailsService;
  DaoAuthenticationProvider daoAuthenticationProvider;

  @Autowired
  public LoginService(UserService userService, JwtUtil jwtTokenUtil,
                      MyUserDetailsService userDetailsService) {
    this.userService = userService;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsService = userDetailsService;
  }


  public UserLoginDTO loginUser(AuthenticationRequest loginRequest) throws UserException {

    //TODO isenabled + exceptionhandling
    if (loginRequest == null || isUsernameMissing(loginRequest) && isPasswordMissing(loginRequest)) {
      throw new MissingParameterException(Arrays.asList("username", "password"));
    }
    checkForMissingLoginParameters(loginRequest);
    final UserDetails userDetails;
    userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
    daoAuthenticationProvider.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));
    final String jwt = jwtTokenUtil.generateToken(userDetails);
    return new UserLoginDTO(new AuthenticationResponse(jwt));
  }


  public void checkForMissingLoginParameters(AuthenticationRequest loginData) throws MissingParameterException {
    List<String> missingParameterList = new ArrayList<>();
    userService.checkIfNullOrEmptyField(loginData.getUsername(), "username", missingParameterList);
    userService.checkIfNullOrEmptyField(loginData.getPassword(), "password", missingParameterList);
    if (missingParameterList.size() > 0) {
      throw new MissingParameterException(missingParameterList);
    }
  }


  public Boolean isUsernameMissing(AuthenticationRequest loginRequest) {
    return (loginRequest.getUsername() == null || loginRequest.getUsername().equals(""));
  }

  public Boolean isPasswordMissing(AuthenticationRequest loginRequest) {
    return (loginRequest.getPassword() == null || loginRequest.getPassword().equals(""));
  }

}
