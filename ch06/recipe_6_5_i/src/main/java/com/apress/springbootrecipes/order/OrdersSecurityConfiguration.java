package com.apress.springbootrecipes.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

@Configuration
public class OrdersSecurityConfiguration {

  @Bean
  SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
    return http
            .authorizeExchange()
             .pathMatchers("/").permitAll()
              .pathMatchers("/orders*").hasRole("USER")
              .anyExchange().authenticated()
            .and().httpBasic()
            .and().formLogin()
            .and().headers()
            .and().logout()
            .and().csrf()
            .and().build();
  }

  private Mono<AuthorizationDecision> ordersAllowed(Mono<Authentication> authentication, AuthorizationContext context) {
    return authentication
            .map(a -> a.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            .map(AuthorizationDecision::new);
  }

  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails marten = User.withDefaultPasswordEncoder()
            .username("marten").password("secret").roles("USER").build();
    UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin").password("admin").roles("USER","ADMIN").build();
    return new MapReactiveUserDetailsService(marten, admin);
  }
}
