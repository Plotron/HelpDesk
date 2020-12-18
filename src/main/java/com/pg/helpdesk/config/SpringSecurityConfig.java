package com.pg.helpdesk.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/", "/home", "/about", "/signup").permitAll()
                .antMatchers("/resources/templates/**").permitAll()
                .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/article/list/**").permitAll()
                .antMatchers("/article/view/**").permitAll()
                .antMatchers("/article/create/**").hasAnyRole("HELPDESK")
                .antMatchers("/article/delete/**").hasAnyRole("HELPDESK")
                .antMatchers("/login").permitAll()
                .antMatchers("/admin/**").hasAnyRole("HELPDESK")
                .and()
                .formLogin().loginPage("/login").successHandler(new RefererRedirectionAuthenticationSuccessHandler())
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }

    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
