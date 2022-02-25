package net.mpqdata.app.mpqdataapi.model.remoteapi;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.UriTemplateRequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchResult;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchResult.RemoteApi;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchRequest;

@Service
@ConfigurationProperties("gcd.search")
public class GcdCoverImageApiClient extends AbstractCoverImageApiClient {

	private static final Map<String,String> REQUEST_PROPERTY_MAP = Map.of(
		"series", "series",
		"issue", "issues",
		"seriesStartYear", "series_year_began"
	);

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Setter
	private String searchUrl;

	@Setter
	@Value("${gcd.issue.cover-page-url-small}")
	private String coverPageUrlSmall;

	@Setter
	@Value("${gcd.issue.cover-page-url-medium}")
	private String coverPageUrlMedium;

	@Setter
	@Value("${gcd.issue.cover-page-url-large}")
	private String coverPageUrlLarge;

	@Setter
	@Value("${gcd.issue.cover-image-path}")
	private String coverImagePath;

	@Setter
	private Map<String, Object> defaultSearchParams;

	@Autowired
	@Setter
	private RestTemplate restTemplate;

	private ParameterizedTypeReference<List<Map<String, Object>>> issueListTypeRef = new ParameterizedTypeReference<>() {};

	@Override
	public List<ImageApiSearchResult> queryForImages(ImageApiSearchRequest request) {

		Map<String, Object> searchParams = new HashMap<>(defaultSearchParams);
		applyRequstToParamMap(request, REQUEST_PROPERTY_MAP, searchParams);
		URI uri = createUriWithQueryString(searchUrl, searchParams);
		logger.debug("gcd search uri: " + uri);

		RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();
		List<ImageApiSearchResult> results = queryApiWithRequestEntity(requestEntity);

		return results;
	}

	private List<ImageApiSearchResult> queryApiWithRequestEntity(RequestEntity<Void> requestEntity) {
		List<Map<String, Object>> searchResults = restTemplate.exchange(requestEntity, issueListTypeRef).getBody();

		List<ImageApiSearchResult> results = new ArrayList<>();
		searchResults.stream()
			.map(map -> map.get("id").toString())
			.map(Integer::parseInt)
			.forEach(id -> {
				ImageApiSearchResult result = fetchByIssueId(id);

				if (result.getImageUrlLarge() == null && result.getImageUrlMedium() == null && result.getImageUrlSmall() == null) {
					return ;
				}

				results.add(result);
			})
		;
		return results;
	}

	private ImageApiSearchResult fetchByIssueId(Integer issueId) {
		Map<String, String> map = Map.of("id", issueId.toString());
		String imgUrlLarge = loadCoverPageAndExtractImageUrl(coverPageUrlLarge, map);
		String imgUrlMedium = loadCoverPageAndExtractImageUrl(coverPageUrlMedium, map);
		String imgUrlSmall = loadCoverPageAndExtractImageUrl(coverPageUrlSmall, map);

		ImageApiSearchResult result = new ImageApiSearchResult();
		result.setRemoteApi(RemoteApi.GCD);
		result.setIssueId( Integer.parseInt( map.get("id") ) );
		result.setImageUrlLarge(imgUrlLarge);
		result.setImageUrlMedium(imgUrlMedium);
		result.setImageUrlSmall(imgUrlSmall);
		return result;
	}

	@Override
	public List<ImageApiSearchResult> queryForImagesByIssueId(int issueId) {
		ImageApiSearchResult result = fetchByIssueId(issueId);
		return List.of(result);
	}

	private String loadCoverPageAndExtractImageUrl(String coverPageUrlTemplate, Map<String, String> map) {
		RequestEntity<Void> requestEntityLarge = UriTemplateRequestEntity.method(HttpMethod.GET, coverPageUrlTemplate, map).build();
		logger.trace("coverPageUrlTemplate: " + coverPageUrlTemplate );
		String html = restTemplate.exchange(requestEntityLarge, String.class).getBody();
		String imgUrl = extractImageUrl(html, coverImagePath, map.get("id"));
		if (imgUrl == null) {
			return null;
		}
		imgUrl = imgUrl.replaceAll("\\?.*", "");
		imgUrl = imgUrl.replaceAll("(\\w)//", "$1/");
		return imgUrl;
	}

	private String extractImageUrl(String html, String coverImagePath, String id) {
		logger.trace("html: \n" + html);

		String cssPath = coverImagePath.replaceAll("\\{id\\}", id);
		Elements imgs = Jsoup.parse(html).select(cssPath);
		if (imgs.size() == 0) {
			return null;
		}
		Element img = imgs.get(0);

		String url = img.attr("src");
		return url;
	}

}
