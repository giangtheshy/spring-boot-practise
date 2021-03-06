package com.practise.config;

import com.practise.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/api/v1/users/**")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/v1/posts/**")
        .permitAll()
        .antMatchers("/api/v1/posts/**")
        .hasAnyAuthority("ROLE_ADMIN")
        .antMatchers("/api/v1/comments/**")
        .permitAll()
        .antMatchers(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .rememberMe()
        .key("remember-me-key-generate-by-giang-the-shy")
        .rememberMeParameter("remember")
        .rememberMeCookieName("rememberlogin")
        .tokenValiditySeconds(1000 * 60 * 60 * 24 * 7);
    httpSecurity.addFilterBefore(
        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
