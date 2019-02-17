package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Lucas Gomes
 * 
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AssociateNotIncludedException extends RuntimeException {

	/**
	 * Constructor
	 *
	 * @param associateId the Associate id
	 * @param votingId    the Voting id
	 */
	public AssociateNotIncludedException(Long associateId, Long votingId) {
		
		super("Associate " + associateId + " not included in voting: " + votingId);
	}

}
