package com.lucas.gomes.votingsystem.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lucasgomes.votingsystem.exceptions.VotingNotFoundException;

@ControllerAdvice
public class VotingNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(VotingNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String votingNotFoundHandler(VotingNotFoundException ex) {
		return ex.getMessage();
	}
}
