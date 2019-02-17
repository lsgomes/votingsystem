package com.lucasgomes.votingsystem.model.entity;

import java.time.Instant;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Lucas
 *
 */

@Data
@Entity
@Table
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Voting {
		
	@Id 
	@GeneratedValue 
	Long id;
	
	String name;
	
	@ManyToMany
	@JoinTable( name = "voting_associate", 
			    joinColumns = @JoinColumn(name = "voting_id"), 
			    inverseJoinColumns = @JoinColumn(name = "associate_id"))
	@EqualsAndHashCode.Exclude
	Set<Associate> associateList;
	
	@OneToMany(mappedBy = "voting")
	Set<AssociateVote> votes;
	
	Instant endTime;
		
	public Voting()
	{
		// For de-serialisation
	}
	
	public Voting(String name)
	{
		this.name = name;
	}
	
	public Voting(String name, Set<Associate> associateList)
	{
		this.name = name;
		this.associateList = associateList;
	}

}
