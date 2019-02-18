package com.lucasgomes.votingsystem.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lucasgomes.votingsystem.exceptions.AssociateNotFoundException;

/**
 * @author Lucas Gomes
 *
 *         Advice for AssociateNotFoundException
 * 
 */
@ControllerAdvice
public class AssociateNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(AssociateNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String handleAssociateNotFoundException(AssociateNotFoundException ex)
	{
		return ex.getMessage();
	}

}
