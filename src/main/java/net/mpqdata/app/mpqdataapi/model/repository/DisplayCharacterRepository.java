package net.mpqdata.app.mpqdataapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.mpqdata.app.mpqdataapi.model.domain.DisplayCharacter;

@Repository
public interface DisplayCharacterRepository extends JpaRepository<DisplayCharacter, String>, JpaSpecificationExecutor<DisplayCharacter> {

}
