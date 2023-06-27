package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class IOServiceStreams implements IOService {
  private final PrintStream output;

  public IOServiceStreams(@Value("#{ T(java.lang.System).out}") PrintStream outputStream) {
    output = outputStream;
  }

  @Override
  public void outputString(String s){
    output.println(s);
  }
}