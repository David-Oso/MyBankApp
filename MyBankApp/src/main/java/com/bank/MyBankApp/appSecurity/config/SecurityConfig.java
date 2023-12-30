package com.bank.MyBankApp.appSecurity.config;

import com.bank.MyBankApp.appSecurity.MyBankAuthenticationEntryPoint;
import com.bank.MyBankApp.appSecurity.MyBankAuthorizationFilter;
import com.bank.MyBankApp.appSecurity.WhiteList;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final MyBankAuthorizationFilter authorizationFilter;
    private final MyBankAuthenticationEntryPoint authenticationEntryPoint;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                WhiteList.freeAccess())
                        .permitAll()
                        .requestMatchers(WhiteList.swagger())
                        .permitAll()

//                        .requestMatchers("/manager/**").hasAnyRole(ADMIN.name(), MANAGER.name())
//                        .requestMatchers(GET, "/manager/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
//                        .requestMatchers(POST, "/manager/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
//                        .requestMatchers(PUT, "/manager/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
//                        .requestMatchers(DELETE, "/manager/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//
//                        .requestMatchers("/admin/**").hasRole(ADMIN.name())
//                        .requestMatchers(GET, "/admin/**").hasAuthority(ADMIN_READ.name())
//                        .requestMatchers(POST, "/admin/**").hasAuthority(ADMIN_CREATE.name())
//                        .requestMatchers(PUT, "/admin/**").hasAuthority(ADMIN_UPDATE.name())
//                        .requestMatchers(DELETE, "/admin/**").hasAuthority(ADMIN_DELETE.name())

                        .anyRequest()
                        .authenticated())
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/*/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
