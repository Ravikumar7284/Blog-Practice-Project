package com.blog.BlogWeb.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "user")
public class User {

  @Id
  @Column(name = "user_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_name", nullable = false, length = 50)
  private String name;

  @Column(name = "user_email", nullable = false, length = 50)
  private String email;

  @Column(name = "user_password", nullable = false, length = 50)
  private String password;

  @Column(name = "user_about", nullable = false)
  private String about;
}
