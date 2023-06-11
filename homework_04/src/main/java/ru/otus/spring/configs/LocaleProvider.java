package ru.otus.spring.configs;

import java.util.Locale;

public interface LocaleProvider {

  Locale getLocale();

  void setLocale(Locale locale);
}
