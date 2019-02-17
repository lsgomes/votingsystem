package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Lucas Gomes
 * 
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class VotingNotFoundException extends RuntimeException {
	
	/**
	 * Constructor
	 * 
	 * @param id the Voting id
	 */
	public VotingNotFoundException(Long id) {
		
		super("Voting not found: " + id);
	}

}
