package com.blog.BlogWeb.config;

import com.blog.BlogWeb.security.CustomUserDetailsService;
import com.blog.BlogWeb.security.JwtAuthenticationEntryPoint;
import com.blog.BlogWeb.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeHttpRequests()

        .antMatchers(Constants.LOGIN_URL).permitAll()
        .antMatchers(Constants.SWAGGER_URLS).permitAll()

        .antMatchers(HttpMethod.GET,Constants.USER_BASE_URL).hasAnyRole(Constants.ADMIN)
        .antMatchers(HttpMethod.GET,Constants.USER_BASE_URL+"/*").hasAnyRole(Constants.ADMIN)
        .antMatchers(HttpMethod.POST,Constants.USER_BASE_URL+"/*/roles/*").hasAnyRole(Constants.ADMIN)
        .antMatchers(HttpMethod.POST,Constants.USER_BASE_URL+"/register").permitAll()
        .antMatchers(HttpMethod.PUT,Constants.USER_BASE_URL+"/*").hasAnyRole(Constants.ADMIN)
        .antMatchers(HttpMethod.DELETE,Constants.USER_BASE_URL+"/*").hasAnyRole(Constants.ADMIN)

        .antMatchers(HttpMethod.GET,Constants.ROLE_BASE_URL).hasAnyRole(Constants.ADMIN)
        .antMatchers(HttpMethod.POST,Constants.ROLE_BASE_URL).hasAnyRole(Constants.ADMIN)
        .antMatchers(HttpMethod.DELETE,Constants.ROLE_BASE_URL+"/*").hasAnyRole(Constants.ADMIN)

        .antMatchers(HttpMethod.GET, Constants.CATEGORY_BASE_URL).permitAll()
        .antMatchers(HttpMethod.GET, Constants.CATEGORY_BASE_URL+"/*").permitAll()
        .antMatchers(HttpMethod.POST, Constants.CATEGORY_BASE_URL).hasAnyRole(Constants.ADMIN, Constants.USER)
        .antMatchers(HttpMethod.PUT, Constants.CATEGORY_BASE_URL+"/*").hasAnyRole(Constants.ADMIN, Constants.USER)
        .antMatchers(HttpMethod.DELETE, Constants.CATEGORY_BASE_URL+"/*").hasAnyRole(Constants.ADMIN, Constants.USER)

        .antMatchers(HttpMethod.POST, Constants.COMMENT_BASE_URL+"/*").hasAnyRole(Constants.ADMIN, Constants.USER)
        .antMatchers(HttpMethod.DELETE, Constants.COMMENT_BASE_URL+"/*").hasAnyRole(Constants.ADMIN, Constants.USER)

        .antMatchers(HttpMethod.GET, Constants.POST_BASE_URL).permitAll()
        .antMatchers(HttpMethod.GET, Constants.POST_BASE_URL+"/*").permitAll()
        .antMatchers(HttpMethod.GET, Constants.POST_BASE_URL+"/users/*").hasAnyRole(Constants.ADMIN,Constants.USER)
        .antMatchers(HttpMethod.GET, Constants.POST_BASE_URL+"categories/*").permitAll()
        .antMatchers(HttpMethod.GET,Constants.POST_BASE_URL+"/search").permitAll()
        .antMatchers(HttpMethod.GET, Constants.POST_BASE_URL+"/file/*").hasAnyRole(Constants.ADMIN,Constants.USER)
        .antMatchers(HttpMethod.POST, Constants.POST_BASE_URL+"/users/*/categories/*").hasAnyRole(Constants.ADMIN, Constants.USER)
        .antMatchers(HttpMethod.POST, Constants.POST_BASE_URL+"/file/upload/*").hasAnyRole(Constants.ADMIN, Constants.USER)
        .antMatchers(HttpMethod.PUT, Constants.POST_BASE_URL+"/*").hasAnyRole(Constants.ADMIN, Constants.USER)
        .antMatchers(HttpMethod.DELETE, Constants.POST_BASE_URL+"/*").hasAnyRole(Constants.ADMIN, Constants.USER)

        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.customUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
