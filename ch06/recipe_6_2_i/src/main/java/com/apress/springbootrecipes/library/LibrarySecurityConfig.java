package com.apress.springbootrecipes.library;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter
        implements WebMvcConfigurer {

  public LibrarySecurityConfig() {
    super(true);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login.html").setViewName("login");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.securityContext()
            .and().exceptionHandling()
            .and().servletApi()
            .and().httpBasic()
            .and().logout()
            .and().headers()
            .and().csrf()
            .and().anonymous().principal("guest").authorities("ROLE_GUEST")
            .and().rememberMe()
            .and().formLogin()
              .loginPage("/login.html")
              .defaultSuccessUrl("/books")
              .failureUrl("/login.html?error=true").permitAll()
            .and().authorizeRequests()
              .mvcMatchers("/").permitAll()
              .anyRequest().authenticated();
  }
}
