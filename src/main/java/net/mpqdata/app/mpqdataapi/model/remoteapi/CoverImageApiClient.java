package net.mpqdata.app.mpqdataapi.model.remoteapi;

import java.util.List;

import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchResult;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchRequest;

public interface CoverImageApiClient {

	public List<ImageApiSearchResult> queryForImages(ImageApiSearchRequest request) ;

	public List<ImageApiSearchResult> queryForImagesByIssueId(int issueId) ;

}
