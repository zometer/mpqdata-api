package net.mpqdata.app.mpqdataapi.model.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayPlayer {

	private String playerName;
	private String allianceName;
	private String allianceRole;

	private List<DisplayCharacter> characters;

}
