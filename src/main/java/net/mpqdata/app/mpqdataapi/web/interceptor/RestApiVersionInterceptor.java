package net.mpqdata.app.mpqdataapi.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import net.mpqdata.app.mpqdataapi.MpqDataApiException;
import net.mpqdata.app.mpqdataapi.web.api.rest.RestApiVersionHolder;

@Component
public class RestApiVersionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (! request.getRequestURI().startsWith("/api/rest/") ) {
			return HandlerInterceptor.super.preHandle(request, response, handler);
		}

		String urlVersion = request.getRequestURI().split("/")[3];
		if (! urlVersion.matches("v(\\w+)") ) {
			throw new MpqDataApiException("REST API calls require a version: " + urlVersion);
		}

		String apiVersion = urlVersion.substring(1);
		RestApiVersionHolder.setRestApiVersion(apiVersion);

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}



}
