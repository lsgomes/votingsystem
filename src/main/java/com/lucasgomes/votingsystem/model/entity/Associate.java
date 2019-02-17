package com.lucasgomes.votingsystem.model.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Associate {

	@Id @GeneratedValue 
	long id;
	
	String name;
	
	@ManyToMany(mappedBy = "associateList")
	@EqualsAndHashCode.Exclude
	Set<Voting> votingList;
	
	@OneToMany(mappedBy = "associate")
	Set<AssociateVote> votes;

	public Associate()
	{
		// For de-serialisation
	}
	
	public Associate(String name)
	{
		this.name = name;
	}
	
	public Associate(String name, Set<Voting> votingList)
	{
		this.name = name;
		this.votingList = votingList;
	}
}
