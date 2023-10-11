package ru.otus.spring.domain;

public class Caterpillar {

  private final String name;

  private final boolean fullyTransformed;

  public Caterpillar(String name, boolean fullyTransformed) {
    this.name = name;
    this.fullyTransformed = fullyTransformed;
  }

  public String getName() {
    return name;
  }

  public boolean isFullyTransformed() {
    return fullyTransformed;
  }
}

