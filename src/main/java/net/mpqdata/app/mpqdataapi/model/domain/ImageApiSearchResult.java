package net.mpqdata.app.mpqdataapi.model.domain;

import lombok.Data;

@Data
public class ImageApiSearchResult {

	public enum RemoteApi { MARVEL, GCD }

	private RemoteApi remoteApi;
	private int issueId;
	private String imageUrlSmall;
	private String imageUrlMedium;
	private String imageUrlLarge;

}
