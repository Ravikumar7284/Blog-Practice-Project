package com.blog.BlogWeb.security;

import com.blog.BlogWeb.config.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenHelper {

  private String secretKey = "blog-web-app";

  //retrieve username from jwt token
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  //retrieve expirate date from jwt token
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  //retrieve any information from token using secretKey
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  //check if the token has expired
  private Boolean isTokenExpired(String token) {
    final Date expriation = getExpirationDateFromToken(token);
    return expriation.before(new Date());
  }

  //generate token for user
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, userDetails.getUsername());
  }

  //generating token including algorithm,date and username
  private String doGenerateToken(Map<String, Object> claims, String username) {
    return Jwts.builder().setClaims(claims).setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + Constants.JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS512, secretKey).compact();
  }

  // token validation
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

}
