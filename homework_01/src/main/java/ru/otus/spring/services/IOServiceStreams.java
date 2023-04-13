package ru.otus.spring.services;

public class IOServiceStreams implements IOService {

  @Override
  public void outputString(String s) {
    System.out.println(s);
  }
}
