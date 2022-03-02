package net.mpqdata.app.mpqdataapi.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(schema="mpq_data", name="display_ability_vw")
public class DisplayAbility {

	@Id
	private Integer abilityId;
	private String abilityKey;

	@Column(name = "mpq_character_key")
	private String mpqCharacterKey;
	private Integer abilitySet;
	private int ordinalPosition;
	private String color;
	private int cost;
	private String name;
	private String description;

	@Transient
	private Integer abilityLevel;

}
