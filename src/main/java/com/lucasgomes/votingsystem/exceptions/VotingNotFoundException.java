package com.lucasgomes.votingsystem.exceptions;

public class VotingNotFoundException extends RuntimeException {

	public VotingNotFoundException(Long id) {
		
		super("Voting not found: " + id);
	}

}
