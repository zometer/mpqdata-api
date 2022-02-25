package net.mpqdata.app.mpqdataapi.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.model.domain.CharacterCover;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchResult;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchResult.RemoteApi;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchRequest;
import net.mpqdata.app.mpqdataapi.model.remoteapi.CoverImageApiClient;
import net.mpqdata.app.mpqdataapi.model.remoteapi.GcdCoverImageApiClient;
import net.mpqdata.app.mpqdataapi.model.remoteapi.MarvelApiClient;
import net.mpqdata.app.mpqdataapi.model.repository.CharacterCoverRepository;

@Service
public class CharacterCoverService {

	private Map<RemoteApi, CoverImageApiClient> apiClientMap = new HashMap<>();

	@Autowired
	@Setter
	private CharacterCoverRepository characterCoverRepository;

	@Autowired
	@Setter
	private MarvelApiClient marvelApiClient;

	@Autowired
	@Setter
	private GcdCoverImageApiClient gcdCoverImageApiClient;

	public CharacterCover fetchCover(long coverId) {
		return characterCoverRepository.findById(coverId).get();
	}

	public List<CharacterCover> fetchUnprocessedCovers() {
		return characterCoverRepository.findByCompleteFalse();
	}

	public CharacterCover save(CharacterCover cover) {
		return characterCoverRepository.save(cover);
	}

	@PostConstruct
	public void loadApiClientMap() {
		apiClientMap.put(RemoteApi.MARVEL, marvelApiClient);
		apiClientMap.put(RemoteApi.GCD, gcdCoverImageApiClient);
	}

	public List<ImageApiSearchResult> searchForCoverImages(RemoteApi remoteApi, ImageApiSearchRequest request) {
		CoverImageApiClient client = apiClientMap.get(remoteApi);
		Assert.notNull(client, "Could not find CoverImageApiClient for RemoteApi: " + remoteApi);

		return client.queryForImages(request);
	}

	public List<ImageApiSearchResult> searchForCoverImagesByIssueId(RemoteApi remoteApi, int issueId) {
		CoverImageApiClient client = apiClientMap.get(remoteApi);
		Assert.notNull(client, "Could not find CoverImageApiClient for RemoteApi: " + remoteApi);

		return client.queryForImagesByIssueId(issueId);
	}

}
