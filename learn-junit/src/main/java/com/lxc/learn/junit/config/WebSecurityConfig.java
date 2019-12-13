/*
package com.lxc.learn.junit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

*/
/**
 * @author lixianchun
 * @Description
 * @date 2019/12/9 21:04
 *//*

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        }

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/index.html","/favicon.ico");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
     */
/*       http.csrf().disable()
                    .authorizeRequests()
                    .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        @Override
                        public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                            o.setAccessDecisionManager(decisionManager);
                            o.setSecurityMetadataSource(urlPathFilterInvocationSecurityMetadataSource);
                            return o;
                        }
                    })

                    .anyRequest()
                    .authenticated()// 其他 url 需要身份认证

                    .and()
                    .formLogin()  //开启登录,如果不指定登录路径(即输入用户名和密码表单提交的路径)，则会默认为spring securtiy的内部定义的路径
                    .successHandler(successAuthenticationHandler)
                    .failureHandler(failureAuthenticationHandler)// 遇到用户名或密码不正确/用户被锁定等情况异常，会交给此handler处理
                    .permitAll()

                    .and()
                    .logout()
                    .logoutUrl("/logout")//退出操作，其实也有一个handler，如果没其他业务逻辑，可以默认为spring security的handler
                    .permitAll()
                    .and()
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler);*//*

        }



}
*/
