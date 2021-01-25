package com.jejujg.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jejujg.security.JwtAuthenticationEntryPoint;
import com.jejujg.security.JwtCheckFilter;
import com.jejujg.security.JwtLoginFilter;
import com.jejujg.security.JwtUtil;
import com.jejujg.service.CookieUtil;
import com.jejujg.service.RedisUtil;
import com.jejujg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //Spring Security 설정할 클래스라고 정의
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.GET, "/api/display")
                .antMatchers(HttpMethod.GET, "/api/goods/**")
                .antMatchers();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        final JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager(), jwtUtil, redisUtil, cookieUtil, objectMapper);
        final JwtCheckFilter jwtCheckFilter = new JwtCheckFilter(jwtUtil, cookieUtil, userService, redisUtil);
        jwtLoginFilter.setFilterProcessesUrl("/user/login");
//        jwtLoginFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));

        http.cors()
                .and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/user/me").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/user/admin").hasRole("ADMIN")
                    .antMatchers("/**").permitAll()
                .and()// UsernamePasswordAuthenticationFilter 전에 JwtAuthenticationFilter를 넣음
                    .addFilter(jwtLoginFilter)
                    .addFilterAfter(jwtCheckFilter, JwtLoginFilter.class)
//                    .addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class)
                ;
    }
}
