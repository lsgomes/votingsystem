package com.lucasgomes.votingsystem.exceptions;

public class VotingAlreadyClosedException extends RuntimeException {

	public VotingAlreadyClosedException(Long id) {
		
		super("Voting already closed: " + id);
	}

}
