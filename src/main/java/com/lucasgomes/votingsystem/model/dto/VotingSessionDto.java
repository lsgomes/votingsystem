package com.lucasgomes.votingsystem.model.dto;

import lombok.Data;

@Data
public class VotingSessionDto {

	Long votingId;
	Long duration;
	

	public VotingSessionDto()
	{
		
	}

	public VotingSessionDto(Long votingId, Long duration)
	{
		this.votingId = votingId;
		this.duration = duration;
	}
}
