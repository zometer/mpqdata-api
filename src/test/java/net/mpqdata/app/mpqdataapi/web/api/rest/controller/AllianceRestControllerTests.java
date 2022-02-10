package net.mpqdata.app.mpqdataapi.web.api.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.mpqdata.app.mpqdataapi.model.domain.Alliance;
import net.mpqdata.app.mpqdataapi.model.service.AllianceService;
import net.mpqdata.app.mpqdataapi.test.junit.ClassNameDisplayNameGenerator;

@DisplayNameGeneration(ClassNameDisplayNameGenerator.class)
@ExtendWith(MockitoExtension.class)
class AllianceRestControllerTests {

	@Mock
	private AllianceService allianceService;

	@Nested
	class SearchAlliancesWithString {

		@Test
		void callsSerivceAndReturnsResults() {
			String searchQuery = "foo";
			List<Alliance> alliances = List.of();
			doReturn(alliances).when(allianceService).searchAlliances(searchQuery);

			AllianceRestController controller = new AllianceRestController();
			controller.setAllianceService(allianceService);

			List<Alliance> results = controller.searchAlliances(searchQuery);

			verify(allianceService).searchAlliances(searchQuery);
			verifyNoMoreInteractions(allianceService);
			assertSame(alliances, results, "unexpected alliance search results");
		}

	}

}
