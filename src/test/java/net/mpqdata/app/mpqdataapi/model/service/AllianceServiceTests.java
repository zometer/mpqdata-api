package net.mpqdata.app.mpqdataapi.model.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity.UriTemplateRequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import net.mpqdata.app.mpqdataapi.MpqDataApiException;
import net.mpqdata.app.mpqdataapi.model.domain.Alliance;
import net.mpqdata.app.mpqdataapi.model.domain.AllianceSearchResult;
import net.mpqdata.app.mpqdataapi.model.domain.AllianceSearchResults;
import net.mpqdata.app.mpqdataapi.test.junit.ClassNameDisplayNameGenerator;

@DisplayNameGeneration(ClassNameDisplayNameGenerator.class)
@ExtendWith(MockitoExtension.class)
class AllianceServiceTests {

	private static final String ALLIANCE_SEARCH_URL = "https://api.local/alliance/search/?q={allianceName}&ExcludeFull={excludeFull}&ExcludePrivate={excludePrivate}";
	private static final String ALLIANCE_INFO_URL = "https://api.local/alliance/{allianceGuid}";
	private static final String DEVICE_ID = "MY_DEVICE_ID";
	private static final String DEVICE_ID_HEADER= "X-My-Device-Id";

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private ResponseEntity<AllianceSearchResults> searchResultsResponseEntity;

	@Mock
	private ResponseEntity<AllianceSearchResult> allianceResponseEntity;

	@Captor
	private ArgumentCaptor<Map<String, Object>> uriMapCaptor;

	@Captor
	private ArgumentCaptor<UriTemplateRequestEntity<Void>> requestEntityCaptor;

	@Captor
	private ArgumentCaptor<ParameterizedTypeReference<List<AllianceSearchResult>>> allianceListTypeRefCaptor;

	@Mock
	private AllianceSearchResults allianceSearchResults;

	@Nested
	class SearchAlliancesWithString {

		@Test
		void callsSearchAllianceWithExcludesFullEqualsFalseAndExcludesPrivateEqualsFalse() {
			AllianceService spyService = spy(new AllianceService());
			String allianceName = "foo";
			List<AllianceSearchResult> list = List.of();
			doReturn(list).when(spyService).searchAlliances(allianceName, false, false);

			spyService.searchAlliances(allianceName);

			verify(spyService).searchAlliances(allianceName, false, false);
		}

	}

	@Nested
	class SearchAlliancesWithStringAndBooleanAndBoolean {

		@Test
		void callsRestTemplateWithUrlWhenNoAlliancesAreFoundInDatabase() {
			String allianceNameSearch = "foo";
			AllianceSearchResult alliance = new AllianceSearchResult();
			// doThrow(EntityNotFoundException.class).when(allianceRepository).fetchByAllianceName(allianceName);
			doReturn(List.of(alliance)).when(allianceSearchResults).getResults();
			doReturn(allianceSearchResults).when(searchResultsResponseEntity).getBody();
			doReturn(searchResultsResponseEntity).when(restTemplate).exchange(requestEntityCaptor.capture(), eq(AllianceSearchResults.class)) ;

			AllianceService service = new AllianceService();
			service.setAllianceSearchUrl(ALLIANCE_SEARCH_URL);
			service.setDeviceIdHeader(DEVICE_ID_HEADER);
			service.setDeviceId(DEVICE_ID);
			service.setRestTemplate(restTemplate);

			boolean excludeFull = false;
			boolean excludePrivate = true;

			List<AllianceSearchResult> results = service.searchAlliances(allianceNameSearch, excludeFull, excludePrivate);

			verify(restTemplate).exchange(requestEntityCaptor.capture(), eq(AllianceSearchResults.class));
			UriTemplateRequestEntity<Void> requestEntity = requestEntityCaptor.getValue();
			assertAll( "Error Validating request",
				() -> { assertEquals(DEVICE_ID, requestEntity.getHeaders().get(DEVICE_ID_HEADER).get(0), "Did not find expected device id header"); },
				() -> { assertEquals(ALLIANCE_SEARCH_URL, requestEntity.getUriTemplate(), "Did not find expected uri template"); },
				() -> { assertEquals(allianceNameSearch, requestEntity.getVarsMap().get("allianceNameSearch"), "Did not find expected allianceName"); },
				() -> { assertEquals(excludeFull, requestEntity.getVarsMap().get("excludeFull"), "Did not find expected 'excludeFull' value"); },
				() -> { assertEquals(excludePrivate, requestEntity.getVarsMap().get("excludePrivate"), "Did not find expected 'excludePrivate' value"); }
			);
			assertAll( "Error validating Results",
				() -> { assertNotNull(results, "Null result list"); },
				() -> { assertEquals(1, results.size(), "Unexpected result size"); },
				() -> { assertSame(alliance, results.get(0), "Unexepect alliance returned"); }
			);
		}

	}

	@Nested
	class FetchAllianceWithString {

		@Test
		void callsRestTemplateAndReturnsResults() {
			String allianceGuid = "foo";
			Alliance alliance = new Alliance();
			// doThrow(EntityNotFoundException.class).when(allianceRepository).fetchByAllianceName(allianceName);
			doReturn(alliance).when(allianceResponseEntity).getBody();
			doReturn(allianceResponseEntity).when(restTemplate).exchange(requestEntityCaptor.capture(), eq(Alliance.class)) ;

			AllianceService service = new AllianceService();
			service.setAllianceInfoUrl(ALLIANCE_INFO_URL);
			service.setDeviceIdHeader(DEVICE_ID_HEADER);
			service.setDeviceId(DEVICE_ID);
			service.setRestTemplate(restTemplate);

			Alliance result = service.fetchAlliance(allianceGuid);

			verify(restTemplate).exchange(requestEntityCaptor.capture(), eq(Alliance.class));
			UriTemplateRequestEntity<Void> requestEntity = requestEntityCaptor.getValue();
			assertAll( "Error Validating request",
				() -> { assertEquals(DEVICE_ID, requestEntity.getHeaders().get(DEVICE_ID_HEADER).get(0), "Did not find expected device id header"); },
				() -> { assertEquals(ALLIANCE_INFO_URL, requestEntity.getUriTemplate(), "Did not find expected uri template"); },
				() -> { assertEquals(allianceGuid, requestEntity.getVarsMap().get("allianceGuid"), "Did not find expected alliance guid"); }
			);
			assertSame(alliance, result, "Unexpected Alliance returned");
		}

	}

	@Nested
	class FetchAllianceByNameWithString {

		private static final String ERROR_NOT_ONE_ALLIANCE = "Expected exactly one alliance for name";

		@Test
		void callsSearchAlliancesMethodAndReturnsAllianceInfoWhenOneIsFound() {
			AllianceService spyService = spy(AllianceService.class);

			String allianceName = "foo";
			String allianceGuid = "guid";
			AllianceSearchResult allianceSearchResult = new AllianceSearchResult();
			Alliance alliance = new Alliance();
			allianceSearchResult.setAllianceGuid(allianceGuid);
			allianceSearchResult.setAllianceName(allianceName);

			doReturn(List.of(allianceSearchResult)).when(spyService).searchAlliances(allianceName);
			doReturn(alliance).when(spyService).fetchAlliance(allianceGuid);

			Alliance returnedAlliance = spyService.fetchAllianceByName(allianceName);

			assertSame(alliance, returnedAlliance, "Unexpected alliance returned.");
		}

		@ParameterizedTest(name = "Test {index}: {0} search results returned")
		@ValueSource(ints = {0, 2, 3})
		void throwsExceptionWhenNumberOfSearchResultsDoesNotEqualOne(int numSearchResults) {
			AllianceService spyService = spy(AllianceService.class);

			String allianceName = "foo";
			String allianceGuid = "guid";
			List<AllianceSearchResult> resultList = new ArrayList<>();
			for (int i=0; i < numSearchResults ; i++) {
				resultList.add(new AllianceSearchResult());
				resultList.get(i).setAllianceGuid(allianceGuid);
			}

			doReturn(resultList).when(spyService).searchAlliances(allianceName);

			MpqDataApiException e = assertThrows(MpqDataApiException.class, () -> spyService.fetchAllianceByName(allianceName));
			assertTrue( e.getMessage().contains(ERROR_NOT_ONE_ALLIANCE), "Expected error message to contain '" + ERROR_NOT_ONE_ALLIANCE + "'");
			assertTrue( e.getMessage().contains(allianceName), "Error message doesn't contain alliance name");
		}

	}
}
