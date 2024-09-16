package com.blog.BlogWeb.controllers;

import com.blog.BlogWeb.dto.JwtAuthRequest;
import com.blog.BlogWeb.dto.JwtAuthResponse;
import com.blog.BlogWeb.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private JwtTokenHelper jwtTokenHelper;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest jwtAuthRequest) {
    this.authenticate(jwtAuthRequest.getEmail(), jwtAuthRequest.getPassword());
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
    String token = this.jwtTokenHelper.generateToken(userDetails);
    JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
    jwtAuthResponse.setToken(token);
    return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
  }

  private void authenticate(String email, String password) throws DisabledException, BadCredentialsException {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
        (email, password);
    this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
  }

}

