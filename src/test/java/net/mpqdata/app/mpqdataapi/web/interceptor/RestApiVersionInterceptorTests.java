package net.mpqdata.app.mpqdataapi.web.interceptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.mpqdata.app.mpqdataapi.MpqDataApiException;
import net.mpqdata.app.mpqdataapi.test.junit.ClassNameDisplayNameGenerator;
import net.mpqdata.app.mpqdataapi.web.api.rest.RestApiVersionHolder;

@DisplayNameGeneration(ClassNameDisplayNameGenerator.class)
@ExtendWith(MockitoExtension.class)
class RestApiVersionInterceptorTests {

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Nested
	class PreHandleWithHttpServletRequestAndHttpServletResponseAndObject {

		@Test
		void setsRestApiVersionWithVersionFromUrl() throws Exception {
			String version = "2";
			String uri = String.format("/api/rest/v%s/hello", version);

			doReturn(uri).when(request).getRequestURI();
			RestApiVersionHolder.remove();

			RestApiVersionInterceptor interceptor = new RestApiVersionInterceptor();

			assertNull(RestApiVersionHolder.getRestApiVersion(), "The REST API version should not be set.");

			interceptor.preHandle(request, response, null);

			assertEquals(version, RestApiVersionHolder.getRestApiVersion(), "Unexpected REST API Version");
		}

		@Test
		void doesNothingOnNonRestApiUrls() throws Exception {
			doReturn("/api/graphql").when(request).getRequestURI();
			RestApiVersionInterceptor interceptor = new RestApiVersionInterceptor();

			RestApiVersionHolder.remove();
			assertNull(RestApiVersionHolder.getRestApiVersion(), "The REST API version should not be set.");

			interceptor.preHandle(request, response, null);

			assertNull(RestApiVersionHolder.getRestApiVersion(), "The REST API version should not be set for non REST API URLs.");
		}

		@Test
		void throwsExceptionOnRestApiUrlWithNoVersion() throws Exception {
			doReturn("/api/rest/api/hello").when(request).getRequestURI();
			RestApiVersionInterceptor interceptor = new RestApiVersionInterceptor();

			RestApiVersionHolder.remove();
			assertNull(RestApiVersionHolder.getRestApiVersion(), "The REST API version should not be set.");

			MpqDataApiException e = assertThrows(MpqDataApiException.class, () -> { interceptor.preHandle(request, response, null); }, "Expected MpqDataApiException");
			assertTrue(e.getMessage().contains("REST API calls require a version"), "Unexepected Exception message");

			assertNull(RestApiVersionHolder.getRestApiVersion(), "The REST API version should not be set for non REST API URLs.");
		}
	}

}
