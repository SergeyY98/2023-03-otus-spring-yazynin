package ru.otus.spring.exceptions;

public class DataLoadingException extends RuntimeException {
  public DataLoadingException(String message, Throwable cause) {
    super(message, cause);
  }
}
