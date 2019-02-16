package com.lucasgomes.votingsystem.exceptions;

public class AssociateNotFoundException extends RuntimeException {

	public AssociateNotFoundException(Long id) {
		
		super("Associate not found: " + id);
	}

}
