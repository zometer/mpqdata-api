package net.mpqdata.app.mpqdataapi.model.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mpqdata.app.mpqdataapi.model.domain.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

	public List<Player> findByPlayerNameIn(Collection<String> playerNames);

	public Player findByPlayerName(String playerName);

}
