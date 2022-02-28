package net.mpqdata.app.mpqdataapi.model.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class RosteredCharacter {

	@JsonAlias("character_identifier")
	private String mpqCharacterId;

	@JsonAlias("effective_level")
	private int effectiveLevel;

	@JsonAlias("ability_levels")
	private List<Integer> abilityLevels;

	@JsonAlias("is_champion")
	private boolean isChampion;

	@JsonAlias("instance_id")
	private long instanceId;

}
