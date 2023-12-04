package ru.otus.spring.service;

public interface MessageService {
  String getMessage(String msgCode);

  String getMessage(String msgCode, String... strings);
}

