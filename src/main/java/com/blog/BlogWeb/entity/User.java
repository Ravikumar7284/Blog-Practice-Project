package com.blog.BlogWeb.entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Post> post = new ArrayList<>();

  @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
  private Set<Comment> comment = new HashSet<>();
}
