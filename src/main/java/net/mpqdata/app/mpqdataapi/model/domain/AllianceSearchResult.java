package net.mpqdata.app.mpqdataapi.model.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AllianceSearchResult extends AbstractAllianceData {

	@JsonAlias("alliance_size")
	private int allianceSize;

}
