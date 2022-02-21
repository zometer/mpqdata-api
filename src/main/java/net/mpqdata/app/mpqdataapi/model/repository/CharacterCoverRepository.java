package net.mpqdata.app.mpqdataapi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mpqdata.app.mpqdataapi.model.domain.CharacterCover;

@Repository
public interface CharacterCoverRepository extends JpaRepository<CharacterCover, Long>{

	public List<CharacterCover> findByCompleteFalse();

}
