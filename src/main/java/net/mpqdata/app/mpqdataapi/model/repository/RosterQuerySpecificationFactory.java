package net.mpqdata.app.mpqdataapi.model.repository;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import net.mpqdata.app.mpqdataapi.model.domain.DisplayCharacter;
import net.mpqdata.app.mpqdataapi.model.domain.RosteredCharacter;

@Component
public class RosterQuerySpecificationFactory {

	public Specification<DisplayCharacter> byRosteredCharacters(List<RosteredCharacter> characters) {
		return (root, query, builder) -> {
			root.fetch("abilities");
			Predicate[] charPredicates = characters.stream()
				.map( c -> {
					Predicate charId = builder.equal(root.get("mpqCharacterKey"), c.getMpqCharacterKey());
					Predicate level = builder.equal(root.get("effectiveLevel"), c.getEffectiveLevel());

					return builder.and(charId, level);
				})
				.collect(toList())
				.toArray( new Predicate[0] )
			;

			return builder.or(charPredicates);
		};
	}

	public Specification<DisplayCharacter> byLocaleLang(String lang) {
		return (root, query, builder) -> builder.equal(root.get("localeLanguage"), lang);
	}

}
