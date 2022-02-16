package net.mpqdata.app.mpqdataapi.web.api.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.model.domain.Alliance;
import net.mpqdata.app.mpqdataapi.model.domain.AllianceSearchResult;
import net.mpqdata.app.mpqdataapi.model.service.AllianceService;
import net.mpqdata.app.mpqdataapi.model.service.PlayerService;

@RestController
public class AllianceRestController {

	@Autowired
	@Setter
	private AllianceService allianceService;

	@Autowired
	@Setter
	private PlayerService playerService;

	@RequestMapping("/api/rest/v{version}/search/alliance")
	public List<AllianceSearchResult> searchAlliances(
		@RequestParam(required = true, value="q") String query,
		@RequestParam(required = false, value="includePrivate", defaultValue = "false") boolean includePrivateAlliances,
		@RequestParam(required = false, value="includeFull", defaultValue = "false") boolean includeFullAlliances
	)
	{
		return allianceService.searchAlliances(query, !includeFullAlliances, !includePrivateAlliances);
	}

	@RequestMapping("/api/rest/v{version}/alliance/{allianceName}")
	public Alliance fetchAllianceByName(@PathVariable("allianceName") String allianceName) {
		Alliance alliance = allianceService.fetchAllianceByName(allianceName);
		playerService.mergeAndSave( alliance.getMembers() );
		return alliance;
	}

	@RequestMapping("/api/rest/v{version}/alliance/guid/{allianceGuid}")
	public Alliance fetchAllianceByGuid(@PathVariable("allianceGuid") String allianceGuid) {
		Alliance alliance = allianceService.fetchAlliance(allianceGuid);
		playerService.mergeAndSave( alliance.getMembers() );
		return alliance;
	}

}
