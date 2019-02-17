package com.lucasgomes.votingsystem.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lucas Gomes
 *
 *         Data Transfer Object for /addassociatetovoting
 *         
 */
@Data
@NoArgsConstructor
public class AddAssociateToVotingDto {

	Long associateId;
	Long votingId;
	
	/**
	 * Constructor
	 * 
	 * @param associateId the Associate id
	 * @param votingId    the Voting id
	 */
	public AddAssociateToVotingDto(Long associateId, Long votingId)
	{
		this.associateId = associateId;
		this.votingId = votingId;
	}
}
