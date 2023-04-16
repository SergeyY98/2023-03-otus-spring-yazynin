package ru.otus.spring.exceptions;

import java.io.IOException;

public class DataLoadingException extends IOException {
  public DataLoadingException(String message, Throwable cause) {
    super(message, cause);
  }
}
