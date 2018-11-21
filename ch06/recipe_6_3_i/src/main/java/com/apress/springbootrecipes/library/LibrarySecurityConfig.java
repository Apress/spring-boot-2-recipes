package com.apress.springbootrecipes.library;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    UserDetails adminUser = User.withDefaultPasswordEncoder()
            .username("admin@books.io")
            .password("secret")
            .authorities("ADMIN","USER").build();

    UserDetails normalUser = User.withDefaultPasswordEncoder()
            .username("marten@books.io")
            .password("user")
            .authorities("USER").build();

    UserDetails disabledUser = User.withDefaultPasswordEncoder()
            .username("marten@books.io")
            .password("user")
            .disabled(true)
            .authorities("USER").build();


    auth.inMemoryAuthentication()
            .withUser(adminUser)
            .withUser(normalUser)
            .withUser(disabledUser);
  }
}
