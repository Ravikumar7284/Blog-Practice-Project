package com.blog.BlogWeb.controllers;

import com.blog.BlogWeb.config.Constants;
import com.blog.BlogWeb.dto.Response;
import com.blog.BlogWeb.entity.Role;
import com.blog.BlogWeb.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

  @Autowired
  private MessageSource messageSource;
  @Autowired
  private RoleService roleService;

  @PostMapping
  public ResponseEntity<Role> createRole(@RequestBody Role request) {
    Role role = this.roleService.createRole(request);
    return new ResponseEntity<Role>(role, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Role>> getAllRoles() {
    return ResponseEntity.ok(this.roleService.getAllRoles());
  }

  @DeleteMapping("/{roleId}")
  public ResponseEntity<Response> deleteRole(@PathVariable Integer roleId) {
    this.roleService.deleteRole(roleId);
    return new ResponseEntity<>(new Response(messageSource.getMessage(Constants.DELETE_SUCCESS,null,null), true), HttpStatus.OK);
  }
}
