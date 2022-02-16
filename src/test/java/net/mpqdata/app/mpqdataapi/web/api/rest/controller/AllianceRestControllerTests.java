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
import net.mpqdata.app.mpqdataapi.model.domain.AllianceSearchResult;
import net.mpqdata.app.mpqdataapi.model.domain.Player;
import net.mpqdata.app.mpqdataapi.model.service.AllianceService;
import net.mpqdata.app.mpqdataapi.model.service.PlayerService;
import net.mpqdata.app.mpqdataapi.test.junit.ClassNameDisplayNameGenerator;

@DisplayNameGeneration(ClassNameDisplayNameGenerator.class)
@ExtendWith(MockitoExtension.class)
class AllianceRestControllerTests {

	@Mock
	private AllianceService allianceService;

	@Mock
	private PlayerService playerService;

	@Nested
	class SearchAlliancesWithStringAndBooleanAndBoolean {

		@Test
		void callsSerivceAndReturnsResults() {
			String searchQuery = "foo";
			List<AllianceSearchResult> alliances = List.of();
			doReturn(alliances).when(allianceService).searchAlliances(searchQuery, true, false);

			AllianceRestController controller = new AllianceRestController();
			controller.setAllianceService(allianceService);

			List<AllianceSearchResult> results = controller.searchAlliances(searchQuery, true, false);

			verify(allianceService).searchAlliances(searchQuery, true, false);
			verifyNoMoreInteractions(allianceService);
			assertSame(alliances, results, "unexpected alliance search results");
		}

	}

	@Nested
	class FetchAllianceByNameWithString {

		@Test
		void callsSerivceAndReturnsResults() {
			String allianceName = "foo";
			Alliance alliance = new Alliance();
			doReturn(alliance).when(allianceService).fetchAllianceByName(allianceName);

			AllianceRestController controller = new AllianceRestController();
			controller.setPlayerService(playerService);
			controller.setAllianceService(allianceService);

			Alliance result = controller.fetchAllianceByName(allianceName);

			verify(allianceService).fetchAllianceByName(allianceName);
			verifyNoMoreInteractions(allianceService);
			assertSame(alliance, result, "unexpected alliance search results");
		}

		@Test
		void savesAllianceMembersAfterAllianceIsReadFromService() {
			String allianceName = "foo";
			Alliance alliance = new Alliance();
			List<Player> members = List.of(new Player());
			alliance.setMembers(members);
			doReturn(alliance).when(allianceService).fetchAllianceByName(allianceName);

			AllianceRestController controller = new AllianceRestController();
			controller.setAllianceService(allianceService);
			controller.setPlayerService(playerService);

			Alliance result = controller.fetchAllianceByName(allianceName);

			verify(allianceService).fetchAllianceByName(allianceName);
			verify(playerService).mergeAndSave(members);
			verifyNoMoreInteractions(allianceService);
			assertSame(alliance, result, "unexpected alliance search results");
		}

	}

	@Nested
	class FetchAllianceWithString {

		@Test
		void callsSerivceAndReturnsResults() {
			String allianceGuid = "foo";
			Alliance alliance = new Alliance();
			doReturn(alliance).when(allianceService).fetchAlliance(allianceGuid);

			AllianceRestController controller = new AllianceRestController();
			controller.setAllianceService(allianceService);
			controller.setPlayerService(playerService);

			Alliance result = controller.fetchAllianceByGuid(allianceGuid);

			verify(allianceService).fetchAlliance(allianceGuid);
			verifyNoMoreInteractions(allianceService);
			assertSame(alliance, result, "unexpected alliance search results");
		}

		@Test
		void savesAllianceMembersAfterAllianceIsReadFromService() {
			String allianceGuid = "foo";
			Alliance alliance = new Alliance();
			List<Player> members = List.of(new Player());
			alliance.setMembers(members);
			doReturn(alliance).when(allianceService).fetchAlliance(allianceGuid);

			AllianceRestController controller = new AllianceRestController();
			controller.setAllianceService(allianceService);
			controller.setPlayerService(playerService);

			Alliance result = controller.fetchAllianceByGuid(allianceGuid);

			verify(allianceService).fetchAlliance(allianceGuid);
			verify(playerService).mergeAndSave(members);
			verifyNoMoreInteractions(allianceService);
			assertSame(alliance, result, "unexpected alliance search results");
		}
	}

}
