package com.lucasgomes.votingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AssociateNotFoundException extends RuntimeException {

	public AssociateNotFoundException(Long id) {
		
		super("Associate not found: " + id);
	}

}
