package net.mpqdata.app.mpqdataapi.model.domain.marvel;

import java.util.List;

import lombok.Data;

@Data
public class ComicDataContainer {

	private int offset;
	private int limit;
	private int total;
	private int count;
	private List<Comic> results;

}
