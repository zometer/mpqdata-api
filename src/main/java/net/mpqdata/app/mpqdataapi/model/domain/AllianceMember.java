package net.mpqdata.app.mpqdataapi.model.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class AllianceMember {

	@JsonAlias("name")
	private String playerName;

	@JsonAlias("guid")
	private String playerGuid;

	@JsonAlias("role")
	private String allianceRole;

}
