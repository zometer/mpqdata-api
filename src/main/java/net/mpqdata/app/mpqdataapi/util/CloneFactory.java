package net.mpqdata.app.mpqdataapi.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.mpqdata.app.mpqdataapi.MpqDataApiException;

@Component
public class CloneFactory {

	private ObjectMapper objectMapper = new ObjectMapper();

	public <T> T clone(T original, Class<T> type) {
		try {
			String json = objectMapper.writeValueAsString(original);
			T clone = objectMapper.readValue(json, type);
			return clone;
		} catch (JsonProcessingException e) {
			throw new MpqDataApiException(e);
		}
	}

}
