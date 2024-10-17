package com.mirror.weblog.jwt.config;

import com.mirror.weblog.jwt.filter.JwtAuthenticationFilter;
import com.mirror.weblog.jwt.handler.RestAuthenticationFailureHandler;
import com.mirror.weblog.jwt.handler.RestAuthenticationSuccessHandler;
import jakarta.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
/**
 * @author mirror
 */
//@Configuration
//public class JwtAuthenticationSecurityConfig implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
@Configuration
@EnableWebSecurity
public class JwtAuthenticationSecurityConfig {
    @Resource
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Resource
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserDetailsService userDetailsService;




//    @Bean
//    @Order(1)
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // 自定义的用于 JWT 身份验证的过滤器
//        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
//        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
//
//        // 设置登录认证对应的处理类（成功处理、失败处理）
//        filter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
//        filter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
//
//        // 直接使用 DaoAuthenticationProvider, 它是 Spring Security 提供的默认的身份验证提供者之一
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        // 设置 userDetailService，用于获取用户的详细信息
//        provider.setUserDetailsService(userDetailsService);
//        // 设置加密算法
//        provider.setPasswordEncoder(passwordEncoder);
//        http.authenticationProvider(provider);
//        // 将这个过滤器添加到 UsernamePasswordAuthenticationFilter 之前执行
//        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();

//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
////                .authenticationProvider(provider)
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(
//                        request -> request
//                                .requestMatchers("/admin/**").authenticated()// 认证所有以 /admin 为前缀的 URL 资源
//                                .anyRequest().permitAll() // 其他都需要放行，无需认证
//                )
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//        ;
//        return http.build();
//    }


    private JwtAuthenticationFilter filter ;

    public void init() {
        // 自定义的用于 JWT 身份验证的过滤器
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();

        // 设置登录认证对应的处理类（成功处理、失败处理）
        filter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        init();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login", "/register", "/kaptcha", "/register/code").permitAll()//无需授权即可访问的url，多个地址可以这样写。
                        .anyRequest().authenticated());
//        http.formLogin(from->from.loginProcessingUrl("/user/login"));
        http.formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

}
