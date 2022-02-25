package net.mpqdata.app.mpqdataapi.model.domain.marvel;

import java.util.List;

import lombok.Data;

@Data
public class CharacterList {

	private int available;
	private int returned;
	private String collectionURI;
	private List<CharacterSummary> items;

}