package com.lucas.gomes.votingsystem.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucasgomes.votingsystem.exceptions.AssociateNotIncludedException;

@ControllerAdvice
public class AssociateNotIncludedAdvice {

	@ResponseBody
	@ExceptionHandler(AssociateNotIncludedException.class)
	String associateAlreadyVotedHandler(AssociateNotIncludedException ex) {
		return ex.getMessage();
	}

}
