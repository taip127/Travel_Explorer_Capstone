package com.tai.travel2.config;

import com.tai.travel2.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/css/**", "/js/**", "/login", "/register", "/home", "/attractions", "/", "/contact", "/destinations","/api/**","/image/**").permitAll()
                        .requestMatchers("/attractions/**").hasRole("ADMIN")
                        .requestMatchers("/attractions", "/checkout", "/receipt").hasAnyRole("BUYER", "ADMIN")
                        .requestMatchers("/api/**").hasRole("VENDOR") // Vendor API access
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/home").permitAll()
                        .successHandler((request, response, authentication) -> {
                            authentication.getAuthorities().forEach(grantedAuthority -> {
                                String role = grantedAuthority.getAuthority();
                                try {
                                    if (role.equals("ROLE_ADMIN")) {
                                        response.sendRedirect("/attractions/manage");
                                    } else if (role.equals("ROLE_BUYER")) {
                                        response.sendRedirect("/attractions");
                                    } else if (role.equals("ROLE_VENDOR")) {
                                        response.sendRedirect("/vendor"); // Redirect for vendors
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        })
                )
                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**") // Disable CSRF for API endpoints
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}