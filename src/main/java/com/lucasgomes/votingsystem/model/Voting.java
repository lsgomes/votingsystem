package com.lucasgomes.votingsystem.model;

import java.time.Instant;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * @author Lucas
 *
 */

@Data
@Entity
public class Voting {
		
	@Id @GeneratedValue 
	Long id;
	
	@ManyToMany
	@JoinTable( name = "voting_associate", 
			    joinColumns = @JoinColumn(name = "voting_id"), 
			    inverseJoinColumns = @JoinColumn(name = "associate_id"))
	Set<Associate> associateList;
	
	@OneToMany(mappedBy = "voting")
	Set<AssociateVote> votes;
	
	private Instant endTime;

}
