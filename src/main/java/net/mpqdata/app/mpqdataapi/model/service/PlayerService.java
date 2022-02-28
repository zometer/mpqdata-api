package net.mpqdata.app.mpqdataapi.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.UriTemplateRequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.model.domain.ExtendedPlayer;
import net.mpqdata.app.mpqdataapi.model.domain.Player;
import net.mpqdata.app.mpqdataapi.model.repository.PlayerRepository;

@Service
public class PlayerService {

	@Setter
	@Value("${mpq.api.deviceid}")
	private String deviceId;

	@Setter
	@Value("${mpq.api.device-id-header}")
	private String deviceIdHeader;

	@Value("${mpq.api.player-info-url}")
	@Setter
	private String playerInfoUrl;

	@Autowired
	@Setter
	private RestTemplate restTemplate;

	@Autowired
	@Setter
	private PlayerRepository playerRepository;

	public List<Player> mergeAndSave(List<Player> members) {
		List<String> playerNames = members.stream().map(p -> p.getPlayerName()).toList();
		List<Player> existingPlayers = playerRepository.findByPlayerNameIn(playerNames);

		for (Player existing: existingPlayers) {
			members.stream()
				.filter( p -> p.getPlayerName().equals( existing.getPlayerName() ) )
				.forEach( p -> p.setPlayerId( existing.getPlayerId() ) )
			;
		}

		playerRepository.saveAll(members);

		return members;
	}

	public ExtendedPlayer fetchByName(String playerName) {
		Player player = playerRepository.findByPlayerName(playerName);

		RequestEntity<Void> requestEntity = UriTemplateRequestEntity
			.method(HttpMethod.GET, playerInfoUrl, Map.of("playerGuid", player.getPlayerGuid()))
			.header(deviceIdHeader, deviceId)
			.build()
		;

		ExtendedPlayer extendedPlayer = restTemplate.exchange(requestEntity, ExtendedPlayer.class).getBody();
		extendedPlayer.getCharacters().forEach( c -> c.setMpqCharacterId( c.getMpqCharacterId().replaceAll("_\\w+", "") ) );
		return extendedPlayer;
	}
}
