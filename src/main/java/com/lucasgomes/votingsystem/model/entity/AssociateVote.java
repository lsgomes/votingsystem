package com.lucasgomes.votingsystem.model.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class AssociateVote {

	@EmbeddedId
	AssociateVoteKey id;
	
	@ManyToOne
    @MapsId("voting_id")
    @JoinColumn(name = "voting_id")
    Voting voting;
 
    @ManyToOne
    @MapsId("associate_id")
    @JoinColumn(name = "associate_id")
    Associate associate;
 
    Boolean vote;
    
    public AssociateVote()
    {
    	// For de-serialisation
    }

    public AssociateVote(Voting voting, Associate associate, Boolean vote)
    {
    	this.voting = voting;
    	this.associate = associate;
    	this.vote = vote;
    }
    

}
