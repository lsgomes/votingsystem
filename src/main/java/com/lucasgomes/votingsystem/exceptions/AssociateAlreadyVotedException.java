package com.lucasgomes.votingsystem.exceptions;

public class AssociateAlreadyVotedException extends RuntimeException {

	public AssociateAlreadyVotedException(Long associateId, Long votingId) {
		
		super("Associate " + associateId + " already voted for voting: " + votingId);
	}

}
