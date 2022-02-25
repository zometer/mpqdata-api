package net.mpqdata.app.mpqdataapi.model.remoteapi;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.UriTemplateRequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.MpqDataApiException;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchResult;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchResult.RemoteApi;
import net.mpqdata.app.mpqdataapi.model.domain.ImageApiSearchRequest;
import net.mpqdata.app.mpqdataapi.model.domain.marvel.Comic;
import net.mpqdata.app.mpqdataapi.model.domain.marvel.ComicDataWrapper;
import net.mpqdata.app.mpqdataapi.model.domain.marvel.Image;

@Service
@ConfigurationProperties("marvel.api")
public class MarvelApiClient extends AbstractCoverImageApiClient {

	private static final Map<String, String> REQUEST_PROPERTY_MAP = Map.of(
		"series", "title",
		"seriesStartYear", "startYear",
		"issue", "issueNumber"
	);

	private static final Map<String, String> IMAGE_SIZE_MAP = Map.of(
		"small", "portrait_medium",
		"medium", "portrait_uncanny",
		"large", "clean"
	);

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Setter
	private String searchUrl;

	@Setter
	private String comicInfoUrl;

	@Value("${marvel.api.publickey}")
	@Setter
	private String publicKey;

	@Value("${marvel.api.privatekey}")
	@Setter
	private String privateKey;

	@Setter
	private Map<String, Object> defaultSearchParams;

	@Autowired
	@Setter
	private RestTemplate restTemplate;

	@Override
	public List<ImageApiSearchResult> queryForImages(ImageApiSearchRequest request) {

		Map<String, Object> searchParams = new HashMap<>(defaultSearchParams);
		addApiFieldsToRequestParams(publicKey, privateKey, searchParams);
		applyRequstToParamMap(request, REQUEST_PROPERTY_MAP, searchParams);
		URI uri = createUriWithQueryString(searchUrl, searchParams);
		logger.debug("searchUri: " + uri);
		RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();

		List<ImageApiSearchResult> results = queryApiWithRequestEntity(requestEntity);

		return results;
	}

	@Override
	public List<ImageApiSearchResult> queryForImagesByIssueId(int issueId) {
		Map<String, Object> params = new HashMap<String, Object>(Map.of("id", issueId));
		addApiFieldsToRequestParams(publicKey, privateKey, params);
		String uriString = createUriStringWithQueryString(comicInfoUrl, params);

		RequestEntity<Void> requestEntity = UriTemplateRequestEntity.method(HttpMethod.GET, uriString, params).build();

		List<ImageApiSearchResult> results = queryApiWithRequestEntity(requestEntity);
		return results;
	}

	private List<ImageApiSearchResult> queryApiWithRequestEntity(RequestEntity<Void> requestEntity) {
		List<ImageApiSearchResult> results = new ArrayList<>();
		ComicDataWrapper wrapper = restTemplate.exchange(requestEntity, ComicDataWrapper.class).getBody();
		wrapper.getData().getResults().forEach( comic -> {
			results.addAll( covertToResult(comic) );

			comic.getVariants().stream()
				.map(variant -> variant.getResourceURI())
				.map(variantUri -> createUriWithQueryString(variantUri, addApiFieldsToRequestParams(publicKey, privateKey, new HashMap<>()) ) )
				.map(variantUri -> restTemplate.getForEntity(variantUri, ComicDataWrapper.class).getBody())
				.map(varWrapper -> varWrapper.getData().getResults())
				.forEach (varComicList -> {
					for (Comic variantComic: varComicList) {
						results.addAll( covertToResult(variantComic) );
					}
				})
			;
		});
		return results;
	}

	protected List<ImageApiSearchResult> covertToResult(Comic comic) {
		List<ImageApiSearchResult> results = new ArrayList<>();
		comic.getImages().stream()
			.map(i -> {
				ImageApiSearchResult result = new ImageApiSearchResult();
				result.setRemoteApi( RemoteApi.MARVEL );
				result.setIssueId( comic.getId() );

				result.setImageUrlLarge( buildImageUrl(i, "large") );
				result.setImageUrlMedium( buildImageUrl(i, "medium") );
				result.setImageUrlSmall( buildImageUrl(i, "small") );

				return result;
			})
			.forEach(results::add);
		;


		return results;
	}

	private String buildImageUrl(Image image, String size) {
		return image.getPath() + "/" + IMAGE_SIZE_MAP.get(size) + "." + image.getExtension();
	}

	public Map<String, Object> addApiFieldsToRequestParams(String publicKey, String privateKey, Map<String, Object> params) {
		Date now = new Date();
		String hash = generateRequestHash(now, publicKey, privateKey);

		params.put("apikey", publicKey);
		params.put("ts", now.getTime());
		params.put("hash", hash);

		return params;
	}

	public String generateRequestHash(Date date, String publicKey, String privateKey) {
		String toBeHashed = date.getTime() + privateKey + publicKey;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new MpqDataApiException(e);
		}
		md.update(toBeHashed.getBytes());
		byte[] digest = md.digest();
		String hash = Hex.encodeHexString(digest, true);
		return hash;
	}

}
