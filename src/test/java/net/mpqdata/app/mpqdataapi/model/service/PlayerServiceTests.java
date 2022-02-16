package net.mpqdata.app.mpqdataapi.model.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.mpqdata.app.mpqdataapi.model.domain.Player;
import net.mpqdata.app.mpqdataapi.model.repository.PlayerRepository;
import net.mpqdata.app.mpqdataapi.test.junit.ClassNameDisplayNameGenerator;

@DisplayNameGeneration(ClassNameDisplayNameGenerator.class)
@ExtendWith(MockitoExtension.class)
class PlayerServiceTests {

	private static final String ZOMETER = "zometer";
	private static final String ZOMETER_GUID = "guid-" + ZOMETER;
	private static final String HELLBOY = "H3LLBOY";
	private static final String HELLBOY_GUID = "guid-" + HELLBOY;
	private static final String STOTTSY = "Stottsy";
	private static final String STOTTSY_GUID = "guid-" + STOTTSY;

	@Captor
	private ArgumentCaptor<List<String>> playerNamesArgumentCaptor;

	@Captor
	private ArgumentCaptor<List<Player>> playerListCaptor;

	@Mock
	private PlayerRepository playerRepository;

	@Nested
	class MergeAndSaveWithListOfPlayers {

		@Test
		void fetchesPlayersFromRepoAndPopulatesIdsForInputList() {
			Player input1 = new Player(null, ZOMETER, ZOMETER_GUID, "Admin");
			Player input2 = new Player(null, HELLBOY, HELLBOY_GUID, "Admin");
			Player input3 = new Player(null, STOTTSY, STOTTSY_GUID, "Member");

			Player dbPlayer1 = new Player(1l, ZOMETER, ZOMETER_GUID, null);
			Player dbPlayer2 = new Player(2l, HELLBOY, HELLBOY_GUID, null);
			List<Player> inputPlayerList = List.of( input1, input2, input3 );

			doReturn(List.of(dbPlayer1, dbPlayer2)).when(playerRepository).findByPlayerNameIn(playerNamesArgumentCaptor.capture());

			PlayerService service = new PlayerService();
			service.setPlayerRepository(playerRepository);

			service.mergeAndSave( inputPlayerList );

			verify(playerRepository).findByPlayerNameIn(playerNamesArgumentCaptor.capture());
			List<String> playerNames = playerNamesArgumentCaptor.getValue();
			assertTrue(playerNames.containsAll( List.of(ZOMETER, HELLBOY, STOTTSY) ), "Expected all names to sent to query method");

			assertAll("Missing expected player db",
				() -> assertEquals(1l, input1.getPlayerId(), "unexpected db id: " + input1.getPlayerName()),
				() -> assertEquals(2l, input2.getPlayerId(), "unexpected db id: " + input2.getPlayerName()),
				() -> assertNull(input3.getPlayerId(), "unexpected db id: " + input3.getPlayerName())
			);
		}

		@Test
		void savesAllPlayersFromInputList() {
			Player input1 = new Player(null, ZOMETER, ZOMETER_GUID, "Admin");
			Player input2 = new Player(null, HELLBOY, HELLBOY_GUID, "Admin");
			Player input3 = new Player(null, STOTTSY, STOTTSY_GUID, "Member");
			List<Player> inputPlayerList = List.of( input1, input2, input3 );

			doReturn( List.of() ).when(playerRepository).findByPlayerNameIn(playerNamesArgumentCaptor.capture());
			doReturn(inputPlayerList).when(playerRepository).saveAll(playerListCaptor.capture());
			InOrder inOrder = Mockito.inOrder(playerRepository);

			PlayerService service = new PlayerService();
			service.setPlayerRepository(playerRepository);

			service.mergeAndSave( List.of(input1, input2, input3) );

			inOrder.verify(playerRepository).findByPlayerNameIn(playerNamesArgumentCaptor.capture());
			inOrder.verify(playerRepository).saveAll(playerListCaptor.capture());
			inOrder.verifyNoMoreInteractions();

			List<String> playerNames = playerNamesArgumentCaptor.getValue();
			assertTrue(playerNames.containsAll( List.of(ZOMETER, HELLBOY, STOTTSY) ), "Expected all names to sent to query method");

			List<Player> savedPlayers = playerListCaptor.getValue();
			assertTrue(savedPlayers.containsAll(inputPlayerList), "Expected all inputPlayerList entries to be saved");
		}
	}

}
