package com.shichko.deliveryservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shichko.deliveryservice.controller.mapper.UserMapper;
import com.shichko.deliveryservice.model.dto.UserDto;
import com.shichko.deliveryservice.model.entity.User;
import com.shichko.deliveryservice.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/profile/**").hasRole("BASIC")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/order/**").permitAll()
                .antMatchers(HttpMethod.GET, "/order").hasRole("ADMIN")
                .antMatchers( "/order/updateState").hasAnyRole("ADMIN", "COURIER")
                .antMatchers(HttpMethod.GET, "/order/**").hasRole("COURIER")
                .antMatchers("/", "/login", "/resources/**", "/js/**", "/ws/**").permitAll()
                .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler((request, response, exception) -> {
                    response.setStatus(HttpStatus.OK.value());
                    response.setHeader("Content-Type", "application/json");
                    UserDetails details = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    UserDto authorizedUser = userMapper.entityToDto((User) userService.loadUserByUsername(details.getUsername()));
                    PrintWriter pw = response.getWriter();
                    ObjectMapper om = new ObjectMapper();
                    String json = om.writeValueAsString(authorizedUser);
                    pw.print(json);
                })
                .failureHandler((request, response, exception) -> response.setStatus(HttpStatus.UNAUTHORIZED.value()))
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http401UnauthorizedEntryPoint())
                .and()
                .logout()
                .invalidateHttpSession(true)
                .permitAll()
                .logoutSuccessUrl("/")
                .and()
                .rememberMe().key("DeliveryServiceSecretKey");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    private static class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

        public Http401UnauthorizedEntryPoint() {
        }

        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException {
            response.sendError(401, "You should login to get access");
        }
    }
}