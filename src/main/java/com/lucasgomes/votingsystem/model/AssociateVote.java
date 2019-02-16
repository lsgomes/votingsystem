package com.lucasgomes.votingsystem.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Data
@Entity
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
 
    boolean vote;

}
