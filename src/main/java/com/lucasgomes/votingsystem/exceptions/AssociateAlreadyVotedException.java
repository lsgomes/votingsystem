package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Lucas Gomes
 * 
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AssociateAlreadyVotedException extends RuntimeException {

	/**
	 * Constructor
	 *
	 * @param associateId the Associate id
	 * @param votingId    the Voting id
	 */
	public AssociateAlreadyVotedException(Long associateId, Long votingId) {
		
		super("Associate " + associateId + " already voted for voting: " + votingId);
	}

}
