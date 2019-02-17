package com.lucasgomes.votingsystem.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lucas Gomes
 *
 *         Data Transfer Object for /vote
 *         
 */
@Data
@NoArgsConstructor
public class VoteDto {

	Long associateId;
	Long votingId;
	Boolean vote;
	
	/**
	 * Constructor
	 * 
	 * @param associateId the Associate id
	 * @param votingId    the Voting id
	 * @param vote        true if agrees with the Voting, otherwise false
	 */
	public VoteDto(Long associateId, Long votingId, Boolean vote)
	{
		this.associateId = associateId;
		this.votingId = votingId;
		this.vote = vote;
	}
}
