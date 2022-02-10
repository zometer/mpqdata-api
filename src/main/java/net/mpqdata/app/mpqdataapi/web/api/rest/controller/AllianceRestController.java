package net.mpqdata.app.mpqdataapi.web.api.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.model.domain.AllianceSearchResult;
import net.mpqdata.app.mpqdataapi.model.service.AllianceService;

@RestController
public class AllianceRestController {

	@Autowired
	@Setter
	private AllianceService allianceService;

	@RequestMapping("/api/rest/v{version}/search/alliance")
	public List<AllianceSearchResult> searchAlliances(@RequestParam(required = true, value="q") String query) {
		return allianceService.searchAlliances(query);
	}

}
