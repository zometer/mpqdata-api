package net.mpqdata.app.mpqdataapi.web.api.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.model.domain.CharacterCover;
import net.mpqdata.app.mpqdataapi.model.service.CharacterCoverService;

@RestController
public class CharacterCoverRestController {

	@Autowired
	@Setter
	private CharacterCoverService characterCoverService;

	@RequestMapping(method=RequestMethod.POST, value="/api/rest/v{version}/covers")
	public CharacterCover save(CharacterCover cover) {
		return characterCoverService.save(cover);
	}

	@RequestMapping("/api/rest/v{version}/covers/unprocessed")
	public List<CharacterCover> fetchUnprocessedCovers() {
		return characterCoverService.fetchUnprocessedCovers();
	}

	@RequestMapping("/api/rest/v{version}/covers/{characterCoverId}")
	public CharacterCover fetchCover(@PathVariable("characterCoverId") long characterCoverId) {
		return characterCoverService.fetchCover(characterCoverId);
	}

}
