package net.mpqdata.app.mpqdataapi.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.MpqDataApiException;
import net.mpqdata.app.mpqdataapi.model.domain.Alliance;
import net.mpqdata.app.mpqdataapi.model.domain.AllianceSearchResult;
import net.mpqdata.app.mpqdataapi.model.domain.AllianceSearchResults;

@Service
public class AllianceService {

	public static final String ERROR_NOT_ONE_ALLIANCE = "Expected exactly one alliance for name: ";

	@Setter
	@Value("${mpq.api.deviceid}")
	private String deviceId;

	@Setter
	@Value("${mpq.api.device-id-header}")
	private String deviceIdHeader;

	@Setter
	@Value("${mpq.api.alliance-info-url}")
	private String allianceInfoUrl;

	@Setter
	@Value("${mpq.api.alliance-search-url}")
	private String allianceSearchUrl;

	@Autowired
	@Setter
	private RestTemplate restTemplate;

	public List<AllianceSearchResult> searchAlliances(String allianceName) {
		return searchAlliances(allianceName, false, false);
	}

	public List<AllianceSearchResult> searchAlliances(String allianceNameSearch, boolean excludeFull, boolean excludePrivate) {
		Map<String, Object> uriVarMap = Map.of("allianceNameSearch", allianceNameSearch, "excludeFull", excludeFull, "excludePrivate", excludePrivate);
		RequestEntity<Void> requestEntity = RequestEntity
			.method(HttpMethod.GET, allianceSearchUrl, uriVarMap)
			.header(deviceIdHeader, deviceId)
			.build()
		;
		ResponseEntity<AllianceSearchResults> responseEntity = restTemplate.exchange(requestEntity, AllianceSearchResults.class);

		return responseEntity.getBody().getResults();
	}

	public Alliance fetchAlliance(String guid) {
		Map<String, Object> uriVarMap = Map.of("allianceGuid", guid);
		RequestEntity<Void> requestEntity = RequestEntity
			.method(HttpMethod.GET, allianceInfoUrl, uriVarMap)
			.header(deviceIdHeader, deviceId)
			.build()
		;

		ResponseEntity<Alliance> responseEntity = restTemplate.exchange(requestEntity, Alliance.class);

		return responseEntity.getBody();
	}

	public Alliance fetchAllianceByName(String allianceName) {
		List<AllianceSearchResult> results = searchAlliances(allianceName);
		if (results.size() != 1) {
			throw new MpqDataApiException(ERROR_NOT_ONE_ALLIANCE + allianceName);
		}

		String allianceGuid = results.get(0).getAllianceGuid();

		return fetchAlliance(allianceGuid);
	}

}
