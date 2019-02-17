package com.lucasgomes.votingsystem.exceptions;

public class AssociateNotIncludedException extends RuntimeException {

	public AssociateNotIncludedException(Long associateId, Long votingId) {
		
		super("Associate " + associateId + " not included in voting: " + votingId);
	}

}
