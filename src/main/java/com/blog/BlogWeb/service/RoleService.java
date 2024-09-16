package com.blog.BlogWeb.service;

import com.blog.BlogWeb.entity.Role;
import com.blog.BlogWeb.exception.ResourceNotFoundException;
import com.blog.BlogWeb.repository.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  public Role createRole(Role request) {
    Role createdRole = this.roleRepository.save(request);
    return createdRole;
  }

  public List<Role> getAllRoles() {
    return this.roleRepository.findAll();
  }

  public void deleteRole(Integer roleId) {
    Role deletedRole = this.roleRepository.findById(roleId).orElseThrow(()-> new ResourceNotFoundException("Role","Id",roleId));
    this.roleRepository.delete(deletedRole);
  }

}
