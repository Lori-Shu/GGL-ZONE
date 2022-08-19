// package com.ggl.cloud.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfigurerAdapter {
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.csrf().disable()
//                 .authorizeRequests()
//                 .antMatchers("/oauth/login/**")
//                 .permitAll()
//                 .antMatchers("/login/**")
//                 .permitAll()
//                 .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                 .anyRequest()
//                 .authenticated()
//                 .and()
//                 .formLogin()
//                 .loginProcessingUrl("/oauth/login")
//                 .loginPage("/oauth/login")
//                 .permitAll();

//     }

//     @Bean
//     public AuthenticationManager authenticationManagerBean() throws Exception {

//         return super.authenticationManagerBean();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// //    @Bean
// //    public UserService userService(){
// //        return new UserService();
// //    }
// }
