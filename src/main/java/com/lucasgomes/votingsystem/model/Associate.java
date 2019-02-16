package com.lucasgomes.votingsystem.model;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Associate {

	private @Id @GeneratedValue long id;
	private Map<Long, Boolean> votingMap;

	public Associate(Long votingId)
	{
		votingMap.put(votingId, null);
	}
	
	public void setVote(Long votingId, Boolean vote)
	{
		votingMap.put(votingId, vote);
	}

	public boolean hasVotingId(long votingId) 
	{
		return votingMap.containsKey(votingId);
	}
	
	public boolean hasAlreadyVoted(long votingId)
	{
		return votingMap.get(votingId) == null ? false : true ;
	}

}
