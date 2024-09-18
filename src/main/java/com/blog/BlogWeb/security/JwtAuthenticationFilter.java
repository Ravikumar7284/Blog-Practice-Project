package com.blog.BlogWeb.security;

import com.blog.BlogWeb.config.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtTokenHelper jwtTokenHelper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // get token
    String requestToken = request.getHeader("Authorization");
    String username = null;
    String token;

    List<String> urlList = Arrays.asList(Constants.LOGIN_URL, Constants.POST_BASE_URL,
        Constants.CATEGORY_BASE_URL, Constants.USER_BASE_URL);
    String requestURI = request.getRequestURI();
    for (String urlString : urlList) {
      if (requestURI.startsWith(urlString)) {
        filterChain.doFilter(request, response); // Continue without validating the JWT
        return;
      }
    }

    if (requestToken != null && requestToken.startsWith("Bearer")) {
      token = requestToken.substring(7);
      try {
        username = this.jwtTokenHelper.getUsernameFromToken(token);
      } catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException exception) {
        exception.getLocalizedMessage();
      }
    } else {
      throw new IllegalArgumentException("Jwt token does not begin with Bearer");
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
        throw new JwtException("Invalid Jwt token");
      }
    } else {
      throw new NullPointerException("Username is null");
    }

    filterChain.doFilter(request, response);
  }
}
