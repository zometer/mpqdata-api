package net.mpqdata.app.mpqdataapi.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(schema="mpq_data", name="player")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long playerId;

	@JsonAlias({"name", "player_name"})
	private String playerName;

	@JsonAlias({"guid", "player_guid"})
	private String playerGuid;

	@JsonAlias({"role", "alliance_role"})
	@Transient
	private String allianceRole;
	
}
