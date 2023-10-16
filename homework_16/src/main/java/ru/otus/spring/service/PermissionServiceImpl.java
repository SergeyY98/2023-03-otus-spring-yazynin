package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;

@Service
public class PermissionServiceImpl implements PermissionService {

  private final MutableAclService mutableAclService;

  @Autowired
  public PermissionServiceImpl(MutableAclService mutableAclService) {
    this.mutableAclService = mutableAclService;
  }

  public void grantPermissions(Book book, long id) {
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
