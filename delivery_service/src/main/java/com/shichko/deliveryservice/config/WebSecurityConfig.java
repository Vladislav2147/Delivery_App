package com.shichko.deliveryservice.config;

import com.shichko.deliveryservice.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //Test
//        httpSecurity
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .failureHandler(new AuthenticationFailureHandler() {
//
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//                                                        AuthenticationException exception) throws IOException, ServletException {
//                        String email = request.getParameter("email");
//                        String error = exception.getMessage();
//                        System.out.println("A failed login attempt with email: "
//                                + email + ". Reason: " + error);
//
//                        String redirectUrl = request.getContextPath() + "/login?error=true";
//                        response.sendRedirect(redirectUrl);
//                    }
//                })
//                //Перенарпавление на главную страницу после успешного входа
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//                .logoutSuccessUrl("/");
        //Valid config
        httpSecurity
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/profile").hasRole("BASIC")
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/order/**").hasRole("COURIER")
                //Доступ разрешен всем пользователей
                .antMatchers("/", "/login", "/resources/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().hasRole("ADMIN")
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
                .successHandler((request, response, exception) -> {
                    response.setStatus(HttpStatus.OK.value());
                    response.setHeader("Content-Type", "application/json");
                    UserDetails details = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    PrintWriter pw = response.getWriter();
                    pw.write(
                            details
                                    .getAuthorities()
                                    .stream()
                                    .map(auth -> "\"" + auth.getAuthority() + "\"")
                                    .collect(Collectors.joining(",", "[", "]"))
                    );
                })
                .failureHandler((request, response, exception) -> response.setStatus(HttpStatus.UNAUTHORIZED.value()))
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http401UnauthorizedEntryPoint())
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
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