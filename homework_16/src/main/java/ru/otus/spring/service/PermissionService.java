package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

public interface PermissionService {
  public void grantPermissions(Book book, long id);
}
