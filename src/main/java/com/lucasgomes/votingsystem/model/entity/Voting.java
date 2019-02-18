package com.lucasgomes.votingsystem.model.entity;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lucas Gomes
 * 
 * Voting entity mapping
 *
 */

@Data
@NoArgsConstructor
@Entity
@Table
//To avoid Jackson infinite recursion
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Voting {
		
	@Id 
	@GeneratedValue()
	private Long id;
	
	@OneToMany( mappedBy = "voting", cascade = { CascadeType.ALL } )
	private Set<AssociateVote> associateVotes;

	private String name;
	 
	private Instant endTime;
	
	private Long numberOfVotes;
	
	/**
	 * Constructor
	 *
	 * @param name the Voting name
	 */
	public Voting(String name)
	{
		this.name = name;
	}

	public Voting(String name, Set<AssociateVote> associateVotes)
	{
		this.name = name;
		this.associateVotes = associateVotes;
	}
	
	public Long incrementNumberOfVotes()
	{
		return numberOfVotes = numberOfVotes + 1;
	}
	
	public Long getNumberOfVotes()
	{
		Long total = 0L;
		
		if (associateVotes != null)
		{
			for (AssociateVote associateVote : associateVotes)
			{
				if (associateVote.getVote() != null)
				{
					total = total + 1;
				}
			}
		}

		return total;
	}

}
