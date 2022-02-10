package net.mpqdata.app.mpqdataapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mpqdata.app.mpqdataapi.model.domain.AllianceSearchResult;

@Repository
public interface AllianceRepository extends JpaRepository<AllianceSearchResult, Long> {

	public AllianceSearchResult findByAllianceName(String allianceName) ;

}
