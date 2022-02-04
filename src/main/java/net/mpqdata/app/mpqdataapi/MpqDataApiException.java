package net.mpqdata.app.mpqdataapi;

public class MpqDataApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MpqDataApiException() {
		super();
	}

	public MpqDataApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MpqDataApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public MpqDataApiException(String message) {
		super(message);
	}

	public MpqDataApiException(Throwable cause) {
		super(cause);
	}

}
