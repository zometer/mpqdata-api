package net.mpqdata.app.mpqdataapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mpqdata.app.mpqdataapi.model.domain.Alliance;

@Repository
public interface AllianceRepository extends JpaRepository<Alliance, Long> {

	public Alliance findByAllianceName(String allianceName) ;

}
