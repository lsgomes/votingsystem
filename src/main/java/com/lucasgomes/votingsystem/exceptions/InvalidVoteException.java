package com.lucasgomes.votingsystem.exceptions;

public class InvalidVoteException extends RuntimeException {

	public InvalidVoteException(Boolean vote) {
		
		super("Invalid vote value: " + vote);
	}

}
