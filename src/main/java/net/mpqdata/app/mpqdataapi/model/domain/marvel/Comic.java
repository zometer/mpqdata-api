package net.mpqdata.app.mpqdataapi.model.domain.marvel;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Comic {

	private int id;
	private int digitalId;
	private String title;
	private double issueNumber;
	private String variantDescription;
	private String description;
	private Date modified;
	private String isbn;
	private String upc;
	private String diamondCode;
	private String ean;
	private String issn;
	private String format;
	private int pageCount;
	private List<TextObject> textObjects;
	private String resourceURI;
	private List<Url> urls;
	private SeriesSummary series;
	private List<ComicSummary> variants;
	private List<ComicSummary> collections;
	private List<ComicSummary> collectedIssues;
	private List<ComicDate> dates;
	private List<ComicPrice> prices;
	private Image thumbnail;
	private List<Image> images;
	private CreatorList creators;
	private CharacterList characters;
	private StoryList stories;
	private EventList events;

}