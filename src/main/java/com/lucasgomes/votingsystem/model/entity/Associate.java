package com.lucasgomes.votingsystem.model.entity;

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
 * Associate entity mapping
 *
 */

@Data
@NoArgsConstructor
@Entity
@Table
// To avoid Jackson infinite recursion
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Associate {

	@Id 
	@GeneratedValue()  
	private Long id;
		
	@OneToMany(mappedBy = "associate", cascade = CascadeType.ALL )
	private Set<AssociateVote> associateVotes;
	
	private String name;
	
	public Associate(String name)
	{
		this.name = name;
	}

}
