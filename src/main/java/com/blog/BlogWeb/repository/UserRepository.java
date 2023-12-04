package com.blog.BlogWeb.repository;

import com.blog.BlogWeb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
