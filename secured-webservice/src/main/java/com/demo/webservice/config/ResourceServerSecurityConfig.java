package com.demo.webservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer //<-- necessary annotation for resource server
public class ResourceServerSecurityConfig extends ResourceServerConfigurerAdapter{
	@Value("${oauth.clientId}")
	private String clientId;
	
	@Value("${oauth.clientSecret}")
	private String clientSecret;
	
	@Value("${oauth.checkTokenUrl}")
	private String checkTokenEndpointUrl;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests()
		//allow all traffic to unsecured endpoint
		.antMatchers("/unsecured").permitAll()
		//require authentication for all other requests
		.anyRequest().authenticated();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(clientId);
	}
	
	@Bean
	public ResourceServerTokenServices tokenService() {
	   RemoteTokenServices tokenServices = new RemoteTokenServices();
	   tokenServices.setClientId(clientId);
	   tokenServices.setClientSecret(clientSecret);
	   tokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
	   return tokenServices;
	}
}
