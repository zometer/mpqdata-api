package net.mpqdata.app.mpqdataapi.model.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractAllianceData {

	@Getter
	@Setter
	@JsonAlias("alliance_guid")
	protected String allianceGuid;

	@Getter
	@Setter
	@JsonAlias("alliance_name")
	protected String allianceName;

	@Getter
	@Setter
	@JsonAlias("alliance_type")
	protected String allianceType;

	@Getter
	@Setter
	@JsonAlias("alliance_max_size")
	protected int allianceMaxSize;


}
