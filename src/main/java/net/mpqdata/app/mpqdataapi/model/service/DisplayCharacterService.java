package net.mpqdata.app.mpqdataapi.model.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.MpqDataApiException;
import net.mpqdata.app.mpqdataapi.model.domain.DisplayAbility;
import net.mpqdata.app.mpqdataapi.model.domain.DisplayCharacter;
import net.mpqdata.app.mpqdataapi.model.domain.RosteredCharacter;
import net.mpqdata.app.mpqdataapi.model.repository.DisplayCharacterRepository;
import net.mpqdata.app.mpqdataapi.model.repository.RosterQuerySpecificationFactory;
import net.mpqdata.app.mpqdataapi.util.CloneFactory;

@Service
public class DisplayCharacterService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Setter
	private RosterQuerySpecificationFactory specificationFactory;

	@Autowired
	@Setter
	private CloneFactory cloneFactory;

	@Autowired
	@Setter
	private DisplayCharacterRepository displayCharacterRepository;

	public List<DisplayCharacter> fetchByLocaleLangAndRosteredCharacters(String lang, List<RosteredCharacter> characters) {
		List<DisplayCharacter> dbChars = displayCharacterRepository.findAll(
			specificationFactory.byLocaleLang(lang)
				.and( specificationFactory.byRosteredCharacters(characters) )
		);

		List<DisplayCharacter> outChars = new ArrayList<>();
		characters.stream().forEach( c -> {
			DisplayCharacter dbChar = dbChars.stream()
				.filter( dbc -> dbc.getEffectiveLevel() == c.getEffectiveLevel() && dbc.getMpqCharacterKey().equals( c.getMpqCharacterKey() ))
				.map(dbc -> cloneFactory.clone(dbc, DisplayCharacter.class) )
				.map(dbc -> {
					logger.debug(c.getMpqCharacterKey() + "-" + c.getEffectiveLevel() + " / " + dbc.getMpqCharacterKey() + "-" + dbc.getEffectiveLevel());
					return dbc;
				})
				.findFirst()
				.orElseThrow( () -> {
					return new MpqDataApiException("Error finding db match for: " + c.getMpqCharacterKey() + "-" + c.getEffectiveLevel());
				})
			;
			dbChar.setInstanceId( c.getInstanceId() );
			dbChar.setChampion( c.isChampion() );
			removeDuplicates(dbChar.getAbilities());
			for (int i=0; i < c.getAbilityLevels().size(); i++) {
				int abilityLevel = convertRawAbilityLevel(c.getAbilityLevels().get(i));
				dbChar.getAbilities().get(i).setAbilityLevel( abilityLevel );
			}
			outChars.add(dbChar);
		});

		return outChars;
	}

	private void removeDuplicates(List<DisplayAbility> abilities) {
		List<Integer> abilityIds = new ArrayList<>();
		List<DisplayAbility> distinct = new ArrayList<>();
		for (DisplayAbility ability : abilities) {
			if (abilityIds.contains( ability.getAbilityId() )) {
				continue;
			}
			abilityIds.add( ability.getAbilityId() );
			distinct.add(ability);
		}
		distinct.sort( (a, b) -> {
			int value = zeroForNull(a.getAbilitySet()).compareTo( zeroForNull(b.getAbilitySet()) );
			if (value != 0) {
				return value;
			}
			return Integer.valueOf(a.getOrdinalPosition()).compareTo( b.getOrdinalPosition() );
		});
		abilities.clear();
		abilities.addAll(distinct);
	}

	private Integer zeroForNull(Integer val) {
		return val == null ? 0 : val;
	}

	public int convertRawAbilityLevel(int rawAbilityLevel) {
		if ( rawAbilityLevel == 0 || rawAbilityLevel == 1) {
			return rawAbilityLevel;
		}
		return (rawAbilityLevel + 5) / 5;
	}

}
