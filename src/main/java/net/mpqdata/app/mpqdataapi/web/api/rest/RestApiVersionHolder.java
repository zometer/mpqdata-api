package net.mpqdata.app.mpqdataapi.web.api.rest;

public final class RestApiVersionHolder {

	private static ThreadLocal<String> restApiVersionThreadLocal = new ThreadLocal<>();

	public static String getRestApiVersion() {
		return  restApiVersionThreadLocal.get();
	}

	public static void setRestApiVersion(String apiVersion) {
		restApiVersionThreadLocal.set(apiVersion);
	}

	public static void remove() {
		restApiVersionThreadLocal.remove();
	}

}
