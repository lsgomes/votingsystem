package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Lucas Gomes
 * 
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidVoteException extends RuntimeException {

	/**
	 * Constructor
	 * 
	 * @param vote to arrive at this exception, must be null
	 */
	public InvalidVoteException(Boolean vote) {		
		super("Invalid vote value: " + vote);
	}

}
