package ru.otus.spring.service;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@Service
public class BookServiceImpl implements BookService {

  private final MutableAclService mutableAclService;

  private final BookRepository bookRepository;

  @Autowired
  public BookServiceImpl(MutableAclService mutableAclService, BookRepository bookRepository) {
    this.mutableAclService = mutableAclService;
    this.bookRepository = bookRepository;
  }

  @Override
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @Override
  public Book findById(long id) {
    try {
      return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    } catch (NoSuchElementException e) {
      return new Book();
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(long id) {
    try {
      Book book = bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
      bookRepository.delete(book);
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void save(Book book) {
    long id = book.getId();
    bookRepository.save(book);

    if (id == 0) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      final Sid owner = new PrincipalSid(authentication);
      ObjectIdentity oid = new ObjectIdentityImpl(book.getClass(), book.getId());

      MutableAcl acl = mutableAclService.createAcl(oid);
      acl.setOwner(owner);
      acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);
      acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, owner, true);
      acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, owner, true);
      acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, owner, true);

      mutableAclService.updateAcl(acl);
    }
  }
}
