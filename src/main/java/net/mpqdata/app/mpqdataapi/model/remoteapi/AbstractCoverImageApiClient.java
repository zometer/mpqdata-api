package net.mpqdata.app.mpqdataapi.model.remoteapi;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import net.mpqdata.app.mpqdataapi.MpqDataApiException;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchRequest;

public abstract class AbstractCoverImageApiClient implements CoverImageApiClient {

	protected void applyRequstToParamMap(ImageApiSearchRequest request, Map<String, String> requestPropToSearchParamMap, Map<String, Object> searchParams) {
		requestPropToSearchParamMap.entrySet().stream()
			.forEach(entry -> {
				try {
					String searchProperty = BeanUtils.getProperty(request, entry.getKey());
					if (StringUtils.isNotBlank(searchProperty)) {
						searchParams.put(entry.getValue(), searchProperty);
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new MpqDataApiException(e);
				}
			})
		;
	}

	protected URI createUriWithQueryString(String urlString, Map<String, Object> params) {
		String uri = createUriStringWithQueryString(urlString, params);
		try {
			return new URI(uri);
		} catch (URISyntaxException e) {
			throw new MpqDataApiException(e);
		}
	}

	protected String createUriStringWithQueryString(String urlString, Map<String, Object> params) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlString);

		params.entrySet().stream()
			.forEach( entry -> builder.queryParam(entry.getKey(), entry.getValue()))
		;

		String uri = builder.encode().build().toUriString();
		return uri;
	}

}
