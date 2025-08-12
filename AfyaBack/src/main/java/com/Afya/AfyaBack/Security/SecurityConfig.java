package com.Afya.AfyaBack.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desabilita CSRF para teste
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/user/register").permitAll()  // libera endpoint /register
                        .anyRequest().authenticated()               // o resto precisa login
                )
                .formLogin(Customizer.withDefaults());         // habilita login padrão

        return http.build();
    }

}