package com.lucasgomes.votingsystem.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Associate {

	@Id @GeneratedValue long id;
	
	@ManyToMany(mappedBy = "associateList") 
	Set<Voting> votingList;
	
	@OneToMany(mappedBy = "associate")
	Set<AssociateVote> votes;
	
}
