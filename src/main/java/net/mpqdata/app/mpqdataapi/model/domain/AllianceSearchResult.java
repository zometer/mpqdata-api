package net.mpqdata.app.mpqdataapi.model.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class AllianceSearchResult {

	private Long allianceId;

	@JsonAlias("alliance_guid")
	private String allianceGuid;

	@JsonAlias("alliance_name")
	private String allianceName;

	@JsonAlias("alliance_type")
	private String allianceType;

	@JsonAlias("alliance_size")
	private int allianceSize;

	@JsonAlias("alliance_max_size")
	private int allianceMaxSize;

}
