package net.mpqdata.app.mpqdataapi.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchResult.RemoteApi;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageApiSearchRequest {

	private String series;
	private Integer seriesStartYear;
	private String issue;
	private String variant;

	private RemoteApi remoteApi;
	private Integer issueId;

}
