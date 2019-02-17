package com.lucas.gomes.votingsystem.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lucasgomes.votingsystem.exceptions.VotingAlreadyClosedException;

@ControllerAdvice
public class VotingAlreadyClosedAdvice {

	@ResponseBody
	@ExceptionHandler(VotingAlreadyClosedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	String votingAlreadyClosedHandler(VotingAlreadyClosedException ex) {
		return ex.getMessage();
	}
}
