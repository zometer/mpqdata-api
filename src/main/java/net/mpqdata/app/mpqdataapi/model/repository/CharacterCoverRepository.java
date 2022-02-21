package net.mpqdata.app.mpqdataapi.model.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.mpqdata.app.mpqdataapi.model.domain.CharacterCover;

@Repository
public interface CharacterCoverRepository extends JpaRepository<CharacterCover, String>{

	public List<CharacterCover> findByCompleteFalse();

	@Modifying
	@Transactional
	@Query(nativeQuery = true)
	public void insertForNewCharacters();

}
