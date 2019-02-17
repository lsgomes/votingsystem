package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AssociateAlreadyVotedException extends RuntimeException {

	public AssociateAlreadyVotedException(Long associateId, Long votingId) {
		
		super("Associate " + associateId + " already voted for voting: " + votingId);
	}

}
