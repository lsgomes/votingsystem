package com.lucasgomes.votingsystem.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucasgomes.votingsystem.exceptions.AssociateNotFoundException;

@ControllerAdvice
public class AssociateNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(AssociateNotFoundException.class)
	String handleAssociateNotFoundException(AssociateNotFoundException ex) {
		return ex.getMessage();
	}

}
