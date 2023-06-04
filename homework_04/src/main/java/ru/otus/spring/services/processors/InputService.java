package ru.otus.spring.services.processors;

public interface InputService {
  int readIntWithPrompt(String prompt);

  String readStringWithPrompt(String prompt);
}
