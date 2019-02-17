package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AssociateNotIncludedException extends RuntimeException {

	public AssociateNotIncludedException(Long associateId, Long votingId) {
		
		super("Associate " + associateId + " not included in voting: " + votingId);
	}

}
