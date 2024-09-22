package com.blog.BlogWeb.security;

import com.blog.BlogWeb.config.Constants;
import com.blog.BlogWeb.exception.ApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.AntPathMatcher;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String BEARER_TOKEN_ERROR = "blog.jwt.invalid.bearer";
  private static final String INVALID_TOKEN = "blog.jwt.invalid.token";
  private static final String EMPTY_USERNAME = "blog.jwt.empty.username";

  @Autowired
  private MessageSource messageSource;
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtTokenHelper jwtTokenHelper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // get token
    String requestToken = request.getHeader(Constants.AUTHORIZATION_HEADER);
    String username = null;
    String token;

    List<String> urlList = new ArrayList<>(Arrays.asList(Constants.LOGIN_URL, Constants.POST_BASE_URL,
        Constants.CATEGORY_BASE_URL, (Constants.USER_BASE_URL + "/register")));
    urlList.addAll(Arrays.asList(Constants.SWAGGER_URLS));
    String requestURI = request.getRequestURI();
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    for (String urlString : urlList) {
      if (antPathMatcher.match(urlString,requestURI)){
        filterChain.doFilter(request, response); // Continue without validating the JWT
        return;
      }
    }

    if (requestToken != null && requestToken.startsWith(Constants.TOKEN_PERFIX)) {
      token = requestToken.substring(7);
      try {
        username = this.jwtTokenHelper.getUsernameFromToken(token);
      } catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException exception) {
        exception.getLocalizedMessage();
      }
    } else {
      throw new ApiException(messageSource.getMessage(BEARER_TOKEN_ERROR,null,request.getLocale()));
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      if (this.jwtTokenHelper.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      } else {
        throw new ApiException(messageSource.getMessage(INVALID_TOKEN,null,request.getLocale()));
      }
    } else {
      throw new ApiException(messageSource.getMessage(EMPTY_USERNAME,null,request.getLocale()));
    }

    filterChain.doFilter(request, response);
  }
}
