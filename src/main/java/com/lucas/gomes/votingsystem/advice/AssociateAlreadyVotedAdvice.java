package com.lucas.gomes.votingsystem.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lucasgomes.votingsystem.exceptions.AssociateAlreadyVotedException;

@ControllerAdvice
public class AssociateAlreadyVotedAdvice {

	@ResponseBody
	@ExceptionHandler(AssociateAlreadyVotedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	String associateAlreadyVotedHandler(AssociateAlreadyVotedException ex) {
		return ex.getMessage();
	}
}