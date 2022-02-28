package net.mpqdata.app.mpqdataapi.model.domain;

import java.util.List;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedPlayer extends Player {

	public ExtendedPlayer(Long playerId, String playerName, String playerGuid, String allianceName, String allianceGuid, String allianceRole)
	{
		super(playerId, playerName, playerGuid, allianceRole);
		this.allianceName = allianceName;
		this.allianceGuid = allianceGuid;
	}

	@Transient
	@JsonAlias("alliance_name")
	private String allianceName;

	@Transient
	@JsonAlias("alliance_guid")
	private String allianceGuid;

	@Transient
	@JsonAlias("roster")
	private List<RosteredCharacter> characters;

}
