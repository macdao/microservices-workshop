package com.example.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterBefore(new OncePerRequestFilter() {
                    @Override
                    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                        final String authorization = request.getHeader("Authorization");
                        if (authorization == null) {
                            filterChain.doFilter(request, response);
                            return;
                        }
                        final String plain = authorization.substring("PLAIN ".length());
                        final Map map = new ObjectMapper().readValue(plain, Map.class);
                        final String name = (String) map.get("name");
                        @SuppressWarnings("unchecked") final List<String> authorities = (List<String>) map.get("authorities");
                        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
                            @Override
                            public Collection<? extends GrantedAuthority> getAuthorities() {
                                return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                            }

                            @Override
                            public Object getCredentials() {
                                return null;
                            }

                            @Override
                            public Object getDetails() {
                                return null;
                            }

                            @Override
                            public Object getPrincipal() {
                                return "";
                            }

                            @Override
                            public boolean isAuthenticated() {
                                return true;
                            }

                            @Override
                            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                            }

                            @Override
                            public String getName() {
                                return name;
                            }
                        });
                        filterChain.doFilter(request, response);
                    }
                }, BasicAuthenticationFilter.class);
    }
}
