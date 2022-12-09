package com.cloud.user.config;

import com.cloud.constants.PermitAllUrl;
import com.cloud.exceptionHandler.Auth2ResponseExceptionTranslator;
import com.cloud.exceptionHandler.AuthExceptionEntryPoint;
import com.cloud.exceptionHandler.MyAccessDeniedHandler;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源服务配置
 * 
 * @author liugh
 *
 */
@EnableResourceServer
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
		http.csrf().disable().exceptionHandling()
				.authenticationEntryPoint(new AuthExceptionEntryPoint())
				.accessDeniedHandler(new MyAccessDeniedHandler())
				.and().requestMatcher(new OAuth2RequestedMatcher()).authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers(PermitAllUrl.permitAllUrl("/swaggerList","/oauth/token","/oauth/user/token","/users-anon/**")).permitAll() // 放开权限的url
				.anyRequest().authenticated().and().httpBasic();
	}


	/**
	 * 判断来源请求是否包含oauth2授权信息<br>
	 * url参数中含有access_token,或者header里有Authorization
	 */
	private static class OAuth2RequestedMatcher implements RequestMatcher {
		@Override
		public boolean matches(HttpServletRequest request) {
			if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
				return true;
			}
			// 请求参数中包含access_token参数
			if (request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) != null) {
				return true;
			}
			// 头部的Authorization值以Bearer开头
			String auth = request.getHeader("Authorization");
			if (auth != null) {
				return auth.startsWith(OAuth2AccessToken.BEARER_TYPE);
			}

			return false;
		}
	}

}
