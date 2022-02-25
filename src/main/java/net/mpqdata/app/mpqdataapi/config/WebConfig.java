package net.mpqdata.app.mpqdataapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.mpqdata.app.mpqdataapi.web.format.StringToRemoteApiConverter;
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

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/rest/**").allowedOrigins("*");
		registry.addMapping("/**");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter( new StringToRemoteApiConverter() );
		WebMvcConfigurer.super.addFormatters(registry);
	}

}
