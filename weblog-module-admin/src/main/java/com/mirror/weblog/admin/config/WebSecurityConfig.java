package com.mirror.weblog.admin.config;

import com.mirror.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author mirror
 */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                .mvcMatchers("/admin/**").authenticated() // 认证所有以 /admin 为前缀的 URL 资源
//                .anyRequest().permitAll().and() // 其他都需要放行，无需认证
//                .formLogin().and() // 使用表单登录
//                .httpBasic(); // 使用 HTTP Basic 认证
//    }
//}
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    /**
//     * 排除静态资源
//     * WebSecurityCustomizer 接口取代了 WebSecurityConfigurerAdapter 接口中的configure(Websecurity web) 方法。
//     * @return
//     */
//    @Bean
//    WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**");
//    }

//    /**
//     * 代替 configure(AuthenticationManagerBuilder auth)
//     * 另外已经被弃用，用到再找
//     * @return
//     */
//    @Bean
//    InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("Admin")
//                .password("admin")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

//    @Resource
//    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    /**
     * 代替  configure
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                // 禁用 csrf
//                .csrf(AbstractHttpConfigurer::disable).
//                // 禁用表单登录
//                formLogin(AbstractHttpConfigurer::disable)
//                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/admin/**").authenticated()// 认证所有以 /admin 为前缀的 URL 资源
                                .anyRequest().permitAll() // 其他都需要放行，无需认证
                )
//                // 前后端分离，无需创建会话;
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}