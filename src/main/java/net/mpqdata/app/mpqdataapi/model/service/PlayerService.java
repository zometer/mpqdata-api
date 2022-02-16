package net.mpqdata.app.mpqdataapi.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.model.domain.Player;
import net.mpqdata.app.mpqdataapi.model.repository.PlayerRepository;

@Service
public class PlayerService {

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
}
