package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidVoteException extends RuntimeException {

	public InvalidVoteException(Boolean vote) {		
		super("Invalid vote value: " + vote);
	}

}
