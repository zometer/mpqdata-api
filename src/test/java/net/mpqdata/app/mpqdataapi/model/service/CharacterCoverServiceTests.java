package net.mpqdata.app.mpqdataapi.model.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.mpqdata.app.mpqdataapi.model.domain.CharacterCover;
import net.mpqdata.app.mpqdataapi.model.repository.CharacterCoverRepository;
import net.mpqdata.app.mpqdataapi.test.junit.ClassNameDisplayNameGenerator;

@DisplayNameGeneration(ClassNameDisplayNameGenerator.class)
@ExtendWith(MockitoExtension.class)
class CharacterCoverServiceTests {

	@Mock
	private CharacterCoverRepository characterCoverRepository;

	@Nested
	class SaveWithCharacterCover {

		@Test
		void callsRepoToSaveTheCover() {
			CharacterCover cover = new CharacterCover();
			doReturn(cover).when(characterCoverRepository).save(cover);

			CharacterCoverService service = new CharacterCoverService();
			service.setCharacterCoverRepository(characterCoverRepository);

			CharacterCover saved = service.save(cover);

			assertSame(cover, saved, "Expected the domain object to be returned.");
			verify(characterCoverRepository).save(cover);
		}

	}

	@Nested
	class FetchUnprocessedCovers {

		@Test
		void callsRepoToQueryUnprocessedCovers() {
			List<CharacterCover> covers = List.of(new CharacterCover());
			doReturn(covers).when(characterCoverRepository).findByCompleteFalse();

			CharacterCoverService service = new CharacterCoverService();
			service.setCharacterCoverRepository(characterCoverRepository);

			List<CharacterCover> unprocessed = service.fetchUnprocessedCovers();

			assertSame(covers, unprocessed, "Expected the domain object to be returned.");
		}

	}

	@Nested
	class FetchCoverWithLong {

		@Test
		void callsRepoToQueryById() {
			Long coverId = 1l;
			CharacterCover cover = new CharacterCover();
			doReturn(cover).when(characterCoverRepository).getById(coverId);

			CharacterCoverService service = new CharacterCoverService();
			service.setCharacterCoverRepository(characterCoverRepository);

			CharacterCover result = service.fetchCover(coverId);

			assertSame(cover, result, "Expected the domain object to be returned.");
		}

	}

}
