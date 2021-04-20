package com.example.security.exception.exception;

public class ReservedUsernameException extends UserException {
  private String requestedName;

  public ReservedUsernameException(String requestedName) {
    this.requestedName = requestedName;
  }

  public ReservedUsernameException() {
  }

  public String getRequestedName() {
    return requestedName;
  }
}
