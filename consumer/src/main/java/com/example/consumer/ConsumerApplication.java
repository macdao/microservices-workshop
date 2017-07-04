package com.example.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ConsumerApplication {

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    @Value("${my.name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }


    @RequestMapping("/c")
    @PreAuthorize("hasRole('USER')")
    public String home(Principal principal) {
        final String response = restTemplate.getForObject("http://provider/p", String.class);
        return "Consumer:Response/" + response + ":config/" + name + ":principal/" + principal.getName();
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .addFilterBefore(new OncePerRequestFilter() {
                        @Override
                        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                            final String authorization = request.getHeader("Authorization");
                            final String plain = authorization.substring("PLAIN ".length());
                            final Map map = new ObjectMapper().readValue(plain, Map.class);
                            final String name = (String) map.get("name");
                            SecurityContextHolder.getContext().setAuthentication(new Authentication() {
                                @Override
                                public Collection<? extends GrantedAuthority> getAuthorities() {
                                    return ImmutableList.of(new SimpleGrantedAuthority("ROLE_USER"));
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
}
