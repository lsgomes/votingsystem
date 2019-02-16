package com.lucasgomes.votingsystem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class AssociateVoteKey implements Serializable {

	@Column(name = "voting_id")
	Long votingId;
	
	@Column(name = "associate_id")
	Long associateId;

	public AssociateVoteKey(long votingId, long associateId) {
		
		this.votingId = votingId;
		this.associateId = associateId;
	}
}
