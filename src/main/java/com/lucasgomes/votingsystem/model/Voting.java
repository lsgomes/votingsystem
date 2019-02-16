package com.lucasgomes.votingsystem.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author Lucas
 *
 */

@Data
@Entity
public class Voting {
		
	private @Id @GeneratedValue long id;
	private Instant endTime;
	private Map<Associate, Boolean> associateMap;
	private int numberOfVotes;
	
	public void addVote()
	{
		this.numberOfVotes = this.numberOfVotes + 1;
	}

	public void addOrReplaceAssociate(Associate associate, Boolean vote)
	{
		associateMap.put(associate, vote);
	}
}
