package com.junior.estudos.estudos.config;

import com.junior.estudos.estudos.entidade.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableConfigurationProperties
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ImplementsUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/gaiola").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST,"/gaiola").hasAnyRole("USER","ADMIN")
                .antMatchers("/passaros").hasAnyRole("USER","ADMIN")
                .antMatchers("/locacao{id}").hasAnyRole("USER","ADMIN")
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/materialize/**").permitAll()
                .antMatchers("/cadastro").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .antMatchers("/editarUsuario{id}").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/login").permitAll()
            .and()
                .logout().logoutSuccessUrl("/login").permitAll()
            .and().rememberMe().userDetailsService(userDetailsService);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
