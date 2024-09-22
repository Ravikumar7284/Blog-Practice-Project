package com.blog.BlogWeb.config;

public class Constants {

  public static final String PAGE_NUMBER = "0";
  public static final String PAGE_SIZE = "3";
  public static final String SORT_BY = "id";
  public static final String SORT_DIRECTION = "asc";
  public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
  public static final String ADMIN = "ADMIN";
  public static final String USER = "USER";
  public static final String POST_BASE_URL = "/api/posts";
  public static final String COMMENT_BASE_URL = "/api/comments";
  public static final String CATEGORY_BASE_URL = "/api/categories";
  public static final String LOGIN_URL = "/api/auth/login";
  public static final String USER_BASE_URL = "/api/users";
  public static final String ROLE_BASE_URL = "/api/roles";
  public static final String DELETE_SUCCESS = "blog.api.delete.success";
  public static final String[] SWAGGER_URLS = {
      "/v3/api-docs",
      "/v2/api-docs",
      "/swagger-resources/**",
      "/swagger-ui/**",
      "/webjars/**"
  };
}
