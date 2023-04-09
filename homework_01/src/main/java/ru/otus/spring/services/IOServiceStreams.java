package ru.otus.spring.services;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceStreams implements IOService {
  private final PrintStream output;

  private final Scanner input;

  public IOServiceStreams(PrintStream outputStream, InputStream inputStream) {
    output = outputStream;
    input = new Scanner(inputStream);
  }

  @Override
  public void outputString(String s) {
    output.println(s);
  }

  @Override
  public String readString() {
    String line = "";
    if (input.hasNextLine()) {
      line = input.nextLine();
    }
    return line;
  }
}
