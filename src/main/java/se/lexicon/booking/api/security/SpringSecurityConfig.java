package se.lexicon.booking.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import se.lexicon.booking.api.exception.MyAccessDeniedHandler;
import se.lexicon.booking.api.exception.MyAuthenticationEntryPoint;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        hasAuthority(‘ROLE_ADMIN') is similar to hasRole(‘ADMIN') because the ‘ROLE_‘ prefix gets added automatically
//        authenticated(): This is the URL you want to protect, and requires the user to login
//        permitAll(): This is used for URL’s with no security applied for example css, javascript
//        hasRole(String role): Restrict to single role. Note that the role will have “ROLE_” appended. So role=”ADMIN” has a comparison against “ROLE_ADMIN”. An alternatve is hasAuthority(String authority)
//        hasAnyRole(String… roles): Allows multiple roles. An alternative is hasAnyAuthority(String… authorities)
//        Other useful methods are:
//        access(String attribute): This method takes SPEL, so you can create more complex restrictions. For those who are interested a lot of the methods in  ExpressionUrlAuthorizationConfigurer.AuthorizedUrl ultimately call access with the required SPEL
//        hasIpAddress(String ipaddressExpression): Restrict on IP address or subnet

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .authenticationEntryPoint(myAuthenticationEntryPoint) // use authenticationEntryPoint will disable the login form
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/", "api/v1/test").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/booking/**").hasAnyRole("PATIENT", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/booking/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/booking/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/booking/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable() //CSRF stands for Cross-Site Request Forgery - It is an attack that forces an end user to execute unwanted actions on a web application in which they are currently authenticated
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler)
        ;

    }

    /**
     *     Authentication usernames and passwords. This time done in memory.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles("PATIENT") //ROLE_PATIENT
                .and()
                .withUser("admin").password("{noop}admin").roles("ADMIN");
    }
}
