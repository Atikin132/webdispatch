package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests(auth -> auth.antMatchers("/",
                        "/index.jsp",
                        "/login.jhtml",
                        "/resources/**").permitAll().antMatchers("/users.jhtml",
                        "/useradd.jhtml",
                        "/useredit.jhtml",
                        "/userdelete.jhtml").hasRole("Administrator").anyRequest().authenticated())

                .formLogin().loginPage("/login.jhtml").loginProcessingUrl("/login.jhtml")
                .usernameParameter("login").passwordParameter("password")
                .defaultSuccessUrl("/welcome.jhtml").failureUrl("/login.jhtml?error=true")
                .permitAll()

                .and()

                .logout().logoutUrl("/logout.jhtml").logoutSuccessUrl("/login.jhtml")
                .invalidateHttpSession(true).clearAuthentication(true).permitAll();

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
