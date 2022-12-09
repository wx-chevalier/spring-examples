package com.cloud.files.config;

import com.cloud.constants.PermitAllUrl;
import com.cloud.exceptionHandler.Auth2ResponseExceptionTranslator;
import com.cloud.exceptionHandler.AuthExceptionEntryPoint;
import com.cloud.exceptionHandler.MyAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;


/**
 * 资源服务配置
 * 
 * @author liugh 53182347@qq.com
 *
 */
//注解@EnableResourceServer帮我们加入了org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter<br>
@EnableResourceServer //每个url就是一种资源
//spring security的注解  prePostEnabled = true表示controller层的 @PreAuthorize("hasAuthority('back:role:save')")开启注解
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// 定义异常转换类生效
		AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
		((OAuth2AuthenticationEntryPoint) authenticationEntryPoint)
				.setExceptionTranslator(new Auth2ResponseExceptionTranslator());
		resources.authenticationEntryPoint(authenticationEntryPoint);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().
				//异常的时候处理 返回SC_UNAUTHORIZED --> 401状态码未授权异常
				exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint())
				.accessDeniedHandler(new MyAccessDeniedHandler())
				.and().authorizeRequests()
				//放行注解url
				.antMatchers(PermitAllUrl.permitAllUrl("/zip/**","/thumbFile/**","/test/**","/sld/**")).permitAll() // 放开权限的url
				.anyRequest().authenticated().and().httpBasic();
	}

	//spring security内置密码加密的bean
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
