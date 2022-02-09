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
import net.mpqdata.app.mpqdataapi.model.domain.Alliance;
import net.mpqdata.app.mpqdataapi.model.domain.AllianceSearchResults;
import net.mpqdata.app.mpqdataapi.model.repository.AllianceRepository;

@Service
public class AllianceService {

	@Setter
	@Value("${mpq.api.deviceid}")
	private String deviceId;

	@Setter
	@Value("${mpq.api.device-id-header}")
	private String deviceIdHeader;

	@Setter
	@Value("${mpq.api.alliance-search-url}")
	private String allianceSearchUrl;

	@Autowired
	@Setter
	private AllianceRepository allianceRepository;

	@Autowired
	@Setter
	private RestTemplate restTemplate;

	public List<Alliance> searchAlliances(String allianceName) {
		return searchAlliances(allianceName, false, false);
	}

	public List<Alliance> searchAlliances(String allianceNameSearch, boolean excludeFull, boolean excludePrivate) {
		Map<String, Object> uriVarMap = Map.of("allianceNameSearch", allianceNameSearch, "excludeFull", excludeFull, "excludePrivate", excludePrivate);
		RequestEntity<Void> requestEntity = RequestEntity
			.method(HttpMethod.GET, allianceSearchUrl, uriVarMap)
			.header(deviceIdHeader, deviceId)
			.build()
		;
		ResponseEntity<AllianceSearchResults> responseEntity = restTemplate.exchange(requestEntity, AllianceSearchResults.class);

		return responseEntity.getBody().getResults();
	}

}
