package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VotingNotFoundException extends RuntimeException {

	public VotingNotFoundException(Long id) {
		
		super("Voting not found: " + id);
	}

}
