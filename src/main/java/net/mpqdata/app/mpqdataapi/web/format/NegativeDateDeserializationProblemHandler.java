package net.mpqdata.app.mpqdataapi.web.format;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

public class NegativeDateDeserializationProblemHandler extends DeserializationProblemHandler {

	@Override
	public Object handleWeirdStringValue(DeserializationContext ctxt, Class<?> targetType, String valueToConvert, String failureMsg)
		throws IOException
	{
		if ( Date.class.isAssignableFrom(targetType) && valueToConvert.startsWith("-")) {
			return null;
		}

		return super.handleWeirdStringValue(ctxt, targetType, valueToConvert, failureMsg);
	}

}
