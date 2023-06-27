package ru.otus.spring.services;

public interface MessageService {
  String getMessage(String msgCode);

  String getMessage(String msgCode, String... strings);
}
