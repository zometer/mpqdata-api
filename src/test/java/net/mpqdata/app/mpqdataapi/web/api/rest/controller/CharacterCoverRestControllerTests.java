package net.mpqdata.app.mpqdataapi.web.api.rest.controller;

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
import net.mpqdata.app.mpqdataapi.model.service.CharacterCoverService;
import net.mpqdata.app.mpqdataapi.test.junit.ClassNameDisplayNameGenerator;

@DisplayNameGeneration(ClassNameDisplayNameGenerator.class)
@ExtendWith(MockitoExtension.class)
class CharacterCoverRestControllerTests {

	@Mock
	private CharacterCoverService characterCoverService;

	@Nested
	class Save {

		@Test
		void callsServiceSaveMethodAndReturnsResults() {
			CharacterCover cover = new CharacterCover();
			doReturn(cover).when(characterCoverService).save(cover);

			CharacterCoverRestController controller = new CharacterCoverRestController();
			controller.setCharacterCoverService(characterCoverService);

			CharacterCover saved = controller.save(cover);

			assertSame(cover, saved, "Expected same cover returned after saving");
			verify(characterCoverService).save(cover);
		}

	}

	@Nested
	class FetchUnprocessedCovers {

		@Test
		void callsServiceFetchMethodAndReturnsResults() {
			List<CharacterCover> covers = List.of( new CharacterCover() );
			doReturn(covers).when(characterCoverService).fetchUnprocessedCovers();

			CharacterCoverRestController controller = new CharacterCoverRestController();
			controller.setCharacterCoverService(characterCoverService);

			List<CharacterCover> unprocessed = controller.fetchUnprocessedCovers();

			assertSame(covers, unprocessed, "Expected same cover returned after saving");
			verify(characterCoverService).fetchUnprocessedCovers();
		}

	}

	@Nested
	class FetchCoverWithLong {

		@Test
		void callsServiceFetchMethodAndReturnsResults() {
			Long coverId = 0l;
			CharacterCover cover = new CharacterCover();
			doReturn(cover).when(characterCoverService).fetchCover(coverId);

			CharacterCoverRestController controller = new CharacterCoverRestController();
			controller.setCharacterCoverService(characterCoverService);

			CharacterCover saved = controller.fetchCover(coverId);

			assertSame(cover, saved, "Expected same cover returned after saving");
			verify(characterCoverService).fetchCover(coverId);
		}

	}

}
