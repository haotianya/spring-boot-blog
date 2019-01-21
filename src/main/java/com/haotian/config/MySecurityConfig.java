package com.haotian.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)  //开启spring security的方法级安全策略
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/js/**","/css/**","/fonts/**","/","/index").permitAll()
		                        .antMatchers("/admins/**").hasRole("ADMIN")//拥有指定权限才可以访问
		                        .antMatchers("/h2-console/**").permitAll()//允许所有来自于指定请求的访问
		                        .and()
		                        .formLogin().loginPage("/login").failureUrl("/login-error")//开启基于表单登陆验证
		                        .and()
		                        .rememberMe().key("haotian.com").rememberMeParameter("remember-me") //开启记住我功能
		                        .and()
		                        .exceptionHandling().accessDeniedPage("/403") //出现异常，重定向到403请求
		                        .and()
		                        .csrf().ignoringAntMatchers("/h2-console") //禁用来自于h2控制台的csrf防护
		                        .and()
		                        .headers().frameOptions().sameOrigin(); //允许来自于同一来源的h2控制台
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		auth.userDetailsService(userDetailsService);
	}
      
	
	
}
