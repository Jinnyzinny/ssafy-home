package com.ssafy.edu.config;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
               .authorizeHttpRequests(auth -> auth
                       .requestMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations())).permitAll()
                       .requestMatchers(
                               HttpMethod.GET,
                               "/resources/**",
                               "/",
                               "/articles",
                               "/articles/search-hashtag",
                               "/css/**","/scripts/**","/plugin/**","/fonts/**"
                       ).permitAll()
                       .requestMatchers("/", "/home", "/user/joinPage", "/joinPage").permitAll()
                       .anyRequest().authenticated()
               )
               .formLogin(Customizer.withDefaults())
               .logout(logout -> logout
                       .logoutSuccessUrl("/")
               );
       return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }
}
