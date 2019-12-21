package org.dsu.dc.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final UsersService usersService;

    @Autowired
    public SecurityConfig(DataSource dataSource, UsersService usersService) {
        this.dataSource = dataSource;
        this.usersService = usersService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config configure() called");

       
        http.authorizeRequests()
                .antMatchers("/article/list").permitAll()
                .antMatchers("/article/read").permitAll()
                .antMatchers("/article/write").hasAnyRole("BASIC", "ADMIN")
                .antMatchers("/article/modify").hasAnyRole("BASIC", "ADMIN")
                .antMatchers("/article/delete").hasAnyRole("BASIC", "ADMIN");

        
        http.formLogin()
                .loginPage("/member/login")
                .successHandler(new LoginSuccessHandler()); 

       
        http.logout()
                .logoutUrl("/member/logout")    
                .logoutSuccessUrl("/member/login")  
                .invalidateHttpSession(true);  
       
        http.rememberMe()
                .key("doubles")
                .userDetailsService(usersService)
                .tokenRepository(getJDBCRepository())
                .tokenValiditySeconds(60*60*24);
    }

    private PersistentTokenRepository getJDBCRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("build auth global ...");

        auth.userDetailsService(usersService).passwordEncoder(passwordEncoder());
    }
}