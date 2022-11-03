package com.example.SWExhibition.security;

import com.example.SWExhibition.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsersService usersService;
    private final AuthenticationFailureHandler customFailureHandler;

    // password 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // css, js 등등 static 관련설정은 무시
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                // user 페이지 설정
                .antMatchers("/")
                .authenticated() // 로그인 필요
                // 기타 url은 모두 허용
                .anyRequest()
                    .permitAll()
                .and()
                // 로그인 페이지 사용
                .formLogin()
                    .loginPage("/login/login") // 로그인 페이지 경로 설정
                    .loginProcessingUrl("/user/login") // 로그인이 실제 이루어지는 곳
                    .failureHandler(customFailureHandler) // 로그인 실패 핸들러
                    .defaultSuccessUrl("/")   // 로그인 성공 후 기본적으로 리다이렉트되는 경로
                .and()
                // 로그아웃
                .logout()
                    .logoutUrl("/user/logout")  // 로그아웃이 실제 이루어지는 곳
                        .permitAll()
                    .logoutSuccessUrl("/") // 로그아웃 성공시 / 로 이동
                    .invalidateHttpSession(true)    // 세션 초기화
                        .deleteCookies("JSESSIONID")    // 쿠키 제거
                ;
        http.sessionManagement()
                .maximumSessions(1) //세션 최대 허용 수
                .maxSessionsPreventsLogin(false) // false이면 중복 로그인하면 이전 로그인이 풀린다.
        ;

    }
}
