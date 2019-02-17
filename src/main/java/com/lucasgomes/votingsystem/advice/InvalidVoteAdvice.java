package com.lucasgomes.votingsystem.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucasgomes.votingsystem.exceptions.InvalidVoteException;

/**
 * @author Lucas Gomes
 *
 *         Advice for InvalidVoteException
 * 
 */
@ControllerAdvice
public class InvalidVoteAdvice {

	@ResponseBody
	@ExceptionHandler(InvalidVoteException.class)
	String associateAlreadyVotedHandler(InvalidVoteException ex) 
	{
		return ex.getMessage();
	}
}
