package net.mpqdata.app.mpqdataapi.web.format;

import org.springframework.core.convert.converter.Converter;

import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchResult.RemoteApi;

public class StringToRemoteApiConverter implements Converter<String, RemoteApi> {

	@Override
	public RemoteApi convert(String source) {
		return RemoteApi.valueOf( source.toUpperCase() );
	}

}
