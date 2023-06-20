package org.ssglobal.training.codes.security;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class RestServiceCorsApplication {
	
	@Autowired
	private MyJwtTokenValidator myJwtTokenValidator;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			   .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			   .and()
			   .cors().configurationSource(new CorsConfigurationSource() {	
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration config = new CorsConfiguration();
						config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
						config.setAllowedMethods(Collections.singletonList("*"));
						config.setAllowCredentials(true);
						config.setAllowedHeaders(Collections.singletonList("*"));
						config.setExposedHeaders(Arrays.asList("Authorization"));
						config.setMaxAge(3600L);
						return config;
					}
			   }).and()
			   	.csrf()
			   	.disable()
			   	.addFilterBefore(myJwtTokenValidator, BasicAuthenticationFilter.class)
			   	.authorizeHttpRequests()
			   	.requestMatchers(new RequestMatcher() {
					@Override
					public boolean matches(HttpServletRequest request) {
						return request.getRequestURI().matches("/*");
					}
			   	})
				.permitAll()
				.and()
				.getOrBuild();
	}
}
