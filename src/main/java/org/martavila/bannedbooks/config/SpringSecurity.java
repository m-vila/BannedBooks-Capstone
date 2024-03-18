package org.martavila.bannedbooks.config;

import org.martavila.bannedbooks.security.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz
                                .requestMatchers("/user-registration/**").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/img/**", "/font/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/books-user-list").permitAll()
                                .requestMatchers("/user-dashboard").permitAll()
                                .requestMatchers("/admin-dashboard").hasRole("ADMIN")
                                .requestMatchers("/books-admin-list").hasRole("ADMIN")
                                .requestMatchers("/book-registration/**").hasRole("ADMIN")
                                .requestMatchers("/registered-users").hasRole("ADMIN")
                                .requestMatchers("/book-update/**").hasRole("ADMIN")
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(new CustomAuthenticationSuccessHandler())
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll());
        return http.build();
    }
}
