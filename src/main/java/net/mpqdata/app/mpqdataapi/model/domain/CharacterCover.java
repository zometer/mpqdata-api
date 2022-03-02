package net.mpqdata.app.mpqdataapi.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(schema = "mpq_data")
public class CharacterCover {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long characterCoverId;

	private String mpqCharacterKey;
	private Long marvelIssueId;
	private Long gcdIssueId;
	private Boolean customCover;
	private Boolean defaultCover;
	private String imageUrlSmall;
	private String imageUrlMedium;
	private String imageUrlLarge;
	private String series;
	private Integer seriesStartYear;
	private String issue;
	private String variant;
	private String mpqEventName;
	private Integer ordinalPosition;
	private boolean complete;

}
