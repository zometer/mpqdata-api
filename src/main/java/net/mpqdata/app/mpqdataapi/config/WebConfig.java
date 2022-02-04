package net.mpqdata.app.mpqdataapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.mpqdata.app.mpqdataapi.web.interceptor.RestApiVersionInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private RestApiVersionInterceptor restApiVersionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(restApiVersionInterceptor);
		WebMvcConfigurer.super.addInterceptors(registry);
	}



}
