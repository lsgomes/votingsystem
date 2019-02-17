package com.lucasgomes.votingsystem.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lucas Gomes
 *
 *         Data Transfer Object for /votingsession
 *         
 */
@Data
@NoArgsConstructor
public class VotingSessionDto {

	Long votingId;
	Long duration;
	
	/**
	 * Constructor 
	 * 
	 * @param votingId the Voting id
	 * @param duration the Voting session duration in seconds
	 */
	public VotingSessionDto(Long votingId, Long duration)
	{
		this.votingId = votingId;
		this.duration = duration;
	}
}
