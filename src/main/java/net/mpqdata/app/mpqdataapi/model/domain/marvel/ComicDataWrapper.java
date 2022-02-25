package net.mpqdata.app.mpqdataapi.model.domain.marvel;

import lombok.Data;

@Data
public class ComicDataWrapper {

	private int code;
	private String status;
	private String copyright;
	private String attributionText;
	private String attributionHTML;
	private ComicDataContainer data;
	private String etag;

}
