package net.mpqdata.app.mpqdataapi.model.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(schema="mpq_data", name="display_character_vw")
@Data
public class DisplayCharacter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String displayCharacterId;
	private String mpqCharacterKey;
	private String name;
	private String subtitle;
	private int rarity;
	private Date releaseDate;
	private String characterBio;
	private int displayLevel;
	private int effectiveLevel;
	private String localeLanguage;
	private String imageUrlSmall;
	private String imageUrlMedium;
	private String imageUrlLarge;

	@Transient
	private Long instanceId;

	@Transient
	private Boolean champion;

	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(
		name="mpq_character_key",
		referencedColumnName="mpqCharacterKey",
		foreignKey=@ForeignKey(value=ConstraintMode.NO_CONSTRAINT),
		insertable=false, updatable=false
	)
	@OrderBy("ordinalPosition")
	private List<DisplayAbility> abilities;
}


