package com.lucasgomes.votingsystem.model.dto;

import lombok.Data;

@Data
public class VoteDto {

	Long associateId;
	Long votingId;
	Boolean vote;
	
	public VoteDto()
	{
		
	}
	
	public VoteDto(Long associateId, Long votingId, Boolean vote)
	{
		this.associateId = associateId;
		this.votingId = votingId;
		this.vote = vote;
	}
}
