package com.lucasgomes.votingsystem.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lucasgomes.votingsystem.exceptions.AssociateNotIncludedException;

/**
 * @author Lucas Gomes
 *
 *         Advice for AssociateNotIncludedException
 * 
 */
@ControllerAdvice
public class AssociateNotIncludedAdvice {

	@ResponseBody
	@ExceptionHandler(AssociateNotIncludedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	String associateAlreadyVotedHandler(AssociateNotIncludedException ex) 
	{
		return ex.getMessage();
	}

}
