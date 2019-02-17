package com.lucas.gomes.votingsystem.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucasgomes.votingsystem.exceptions.VotingAlreadyClosedException;

@ControllerAdvice
public class VotingAlreadyClosedAdvice {

	@ResponseBody
	@ExceptionHandler(VotingAlreadyClosedException.class)
	String votingAlreadyClosedHandler(VotingAlreadyClosedException ex) {
		return ex.getMessage();
	}
}
