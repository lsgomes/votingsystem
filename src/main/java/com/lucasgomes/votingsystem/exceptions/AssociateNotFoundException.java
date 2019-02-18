package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Lucas Gomes
 * 
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssociateNotFoundException extends RuntimeException {

	/**
	 * Constructor
	 *
	 * @param id the Associate id
	 */
	public AssociateNotFoundException(Long id) {
		
		super("Associate not found: " + id);
	}

}
