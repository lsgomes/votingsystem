package com.lucasgomes.votingsystem.model.dto;

import lombok.Data;

@Data
public class AddAssociateToVotingDto {

	Long associateId;
	Long votingId;
	
	public AddAssociateToVotingDto()
	{
		
	}
	
	public AddAssociateToVotingDto(Long associateId, Long votingId)
	{
		this.associateId = associateId;
		this.votingId = votingId;
	}
}
