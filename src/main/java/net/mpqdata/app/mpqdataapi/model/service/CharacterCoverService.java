package net.mpqdata.app.mpqdataapi.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.model.domain.CharacterCover;
import net.mpqdata.app.mpqdataapi.model.repository.CharacterCoverRepository;

@Service
public class CharacterCoverService {

	@Autowired
	@Setter
	private CharacterCoverRepository characterCoverRepository;

	public CharacterCover fetchCover(long coverId) {
		return characterCoverRepository.getById(coverId);
	}

	public List<CharacterCover> fetchUnprocessedCovers() {
		return characterCoverRepository.findByCompleteFalse();
	}

	public CharacterCover save(CharacterCover cover) {
		return characterCoverRepository.save(cover);
	}

}
