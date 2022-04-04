package se.lexicon.booking.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.lexicon.booking.api.exception.MyAccessDeniedHandler;
import se.lexicon.booking.api.exception.MyAuthenticationEntryPoint;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/api/test/").permitAll() // URL’s has no security
                .antMatchers(HttpMethod.GET, "/api/v1/booking/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v1/booking/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/booking/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/booking/**").hasRole("ADMIN")
                .anyRequest().authenticated()

                .and()
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler)
        ;
    }


    //https://www.javaguides.net/2018/09/spring-boot-spring-mvc-role-based-spring-security-jpa-thymeleaf-mysql-tutorial.html
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

    }

}