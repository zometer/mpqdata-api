package net.mpqdata.app.mpqdataapi.web.api.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.model.domain.DisplayCharacter;
import net.mpqdata.app.mpqdataapi.model.domain.ExtendedPlayer;
import net.mpqdata.app.mpqdataapi.model.service.DisplayCharacterService;
import net.mpqdata.app.mpqdataapi.model.service.PlayerService;

@RestController
public class RosterRestController {

	private static final String DEFAULT_LANGUAGE = "en";

	@Autowired
	@Setter
	private PlayerService playerService;

	@Autowired
	@Setter
	private DisplayCharacterService displayCharacterService;

	@RequestMapping("/api/rest/v{version}/roster/{playerName}")
	public List<DisplayCharacter> fetchRosterByName(@PathVariable("playerName") String playerName) {
		ExtendedPlayer player = playerService.fetchByName(playerName);
		List<DisplayCharacter> characters = displayCharacterService.fetchByLocaleLangAndRosteredCharacters(DEFAULT_LANGUAGE, player.getCharacters());
		return characters;
	}

}
