package com.example.security.exception.handler;


import com.example.security.exception.exception.MissingParameterException;
import com.example.security.exception.exception.NotValidEMailException;
import com.example.security.exception.exception.NotValidatedUserException;
import com.example.security.exception.exception.ReservedEMailException;
import com.example.security.exception.exception.ReservedUsernameException;
import com.example.security.model.ErrorDTO;
import com.example.security.model.login.UserLoginDTO;
import com.example.security.model.register.RegisterResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(ReservedUsernameException.class)
  public ResponseEntity<RegisterResponseDTO> reservedUsernameExceptionHandling(ReservedUsernameException ex) {
    return new ResponseEntity<>(new RegisterResponseDTO("Username already taken, please choose an other one."),
        HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MissingParameterException.class)
  public ResponseEntity<RegisterResponseDTO> missingParameterExceptionHandling(MissingParameterException ex) {
    String message = "Missing parameter(s): " + String.join(", ", ex.getMissingParameterList());
    return new ResponseEntity<>(new RegisterResponseDTO(message), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<UserLoginDTO> usernameNotFoundExceptionHandling() {
    return new ResponseEntity<>(new UserLoginDTO("No such user can be found!"), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotValidatedUserException.class)
  public ResponseEntity<UserLoginDTO> notValidatedUserExceptionHandling() {
    return new ResponseEntity<>(new UserLoginDTO("Validate your e-mail first!"), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotValidEMailException.class)
  public ResponseEntity<RegisterResponseDTO> notValidEMailExceptionHandling() {
    return new ResponseEntity<>(new RegisterResponseDTO("E-mail is not valid!"), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<UserLoginDTO> badCredentialsExceptionHandling() {
    return new ResponseEntity<>(new UserLoginDTO("Wrong password!"), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorDTO> httpMessageNotReadableExceptionHandling(HttpMessageNotReadableException ex) {
    return new ResponseEntity<>(new ErrorDTO("The message is not readable."),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ReservedEMailException.class)
  public ResponseEntity<RegisterResponseDTO> reservedEMailException(ReservedEMailException ex) {
    return new ResponseEntity<>(new RegisterResponseDTO("Username already taken, please choose an other one."),
        HttpStatus.CONFLICT);
  }
}