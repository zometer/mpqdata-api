package net.mpqdata.app.mpqdataapi.model.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Alliance extends AbstractAllianceData {

	@JsonAlias("alliance_members")
	private List<AllianceMember> members;

}
