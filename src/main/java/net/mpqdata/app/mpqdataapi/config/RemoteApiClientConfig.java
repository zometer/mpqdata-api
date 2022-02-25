package net.mpqdata.app.mpqdataapi.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.mpqdata.app.mpqdataapi.web.format.NegativeDateDeserializationProblemHandler;

@Configuration
public class RemoteApiClientConfig {

	@Bean
	public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		restTemplate.getMessageConverters().stream()
			.filter( coverter -> AbstractJackson2HttpMessageConverter.class.isInstance(coverter) )
			.map( coverter -> (AbstractJackson2HttpMessageConverter) coverter )
			.forEach( coverter -> {
				ObjectMapper objectMapper = coverter.getObjectMapper();
				DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
				deserializationConfig = deserializationConfig.withHandler( new NegativeDateDeserializationProblemHandler() ) ;
				objectMapper.setConfig(deserializationConfig);
			})
		;

		return restTemplate;
	}

}
