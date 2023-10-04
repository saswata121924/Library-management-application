package com.example.librarymanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class UserConfig {
    @Value("${user.authority.student}")
    private String studentAuthority;

    @Value("${user.authority.admin}")
    private String adminAuthority;

    @Autowired
    UserService userService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(getPE());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"/book/**").hasAnyAuthority(adminAuthority, studentAuthority)
                        .requestMatchers("/book/**").hasAuthority(adminAuthority)
                        .requestMatchers(HttpMethod.GET,"/transaction/**").hasAnyAuthority(adminAuthority, studentAuthority)
                        .requestMatchers("/transaction/**").hasAuthority(studentAuthority)
                        .requestMatchers(HttpMethod.POST, "/student/**").permitAll()
                        .requestMatchers("/student/details").hasAnyAuthority(studentAuthority, adminAuthority)
                        .requestMatchers("/student/**", "/student/all/**").hasAuthority(adminAuthority)
                        .requestMatchers("/**").permitAll()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults());
        return http.build();
    }
    @Bean
    public PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }
}
