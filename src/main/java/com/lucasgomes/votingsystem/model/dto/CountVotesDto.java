package com.lucasgomes.votingsystem.model.dto;

import lombok.Data;

@Data
public class CountVotesDto {
	
	Long votingId;
	
	public CountVotesDto()
	{
		
	}
	
	public CountVotesDto(Long votingId)
	{
		this.votingId = votingId;
	}

}
