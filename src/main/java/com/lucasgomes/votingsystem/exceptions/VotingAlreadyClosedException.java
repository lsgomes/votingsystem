package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Lucas Gomes
 * 
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class VotingAlreadyClosedException extends RuntimeException {

	/**
	 * Constructor
	 * 
	 * @param id the Voting id
	 */
	public VotingAlreadyClosedException(Long id) {
		
		super("Voting already closed: " + id);
	}

}
