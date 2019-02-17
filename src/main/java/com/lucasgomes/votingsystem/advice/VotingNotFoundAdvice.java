package com.lucasgomes.votingsystem.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucasgomes.votingsystem.exceptions.VotingNotFoundException;

/**
 * @author Lucas Gomes
 *
 *         Advice for VotingNotFoundException
 * 
 */
@ControllerAdvice
public class VotingNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(VotingNotFoundException.class)
	String votingNotFoundHandler(VotingNotFoundException ex) 
	{
		return ex.getMessage();
	}
}
