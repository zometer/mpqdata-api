package net.mpqdata.app.mpqdataapi.model.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Entity
@Table(schema="mpq_data")
@Data
public class Alliance {

	@Id
	private Long allianceId;

	@JsonAlias("alliance_guid")
	private String allianceGuid;

	@JsonAlias("alliance_name")
	private String allianceName;

	@Transient
	@JsonAlias("alliance_type")
	private String allianceType;

	@Transient
	@JsonAlias("alliance_size")
	private int allianceSize;

	@Transient
	@JsonAlias("alliance_max_size")
	private int allianceMaxSize;

}
