package com.lucasgomes.votingsystem.model.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Lucas Gomes
 * 
 * AssociateVote entity mapping
 *
 */

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@IdClass(AssociateVoteKey.class)
public class AssociateVote {
	
	@Id
	@ManyToOne
    @JoinColumn()
    Voting voting;
 
	@Id
    @ManyToOne
    @JoinColumn()
    Associate associate;
 
    Boolean vote;

    /**
     * Constructor
     * 
     * @param voting the Voting entity
     * @param associate the Associate entity
     * @param vote true, false or null. null counts as not voted
     */
    public AssociateVote(Voting voting, Associate associate, Boolean vote)
    {
    	this.voting = voting;
    	this.associate = associate;
    	this.vote = vote;
    }
    
    public AssociateVote(Voting voting, Associate associate)
    {
    	this.voting = voting;
    	this.associate = associate;
    }
    
    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (!(o instanceof AssociateVote)) return false;
        AssociateVote that = (AssociateVote) o;
        return Objects.equals(voting.getName(), that.voting.getName()) &&
               Objects.equals(associate.getName(), that.associate.getName()) &&
               Objects.equals(vote, that.vote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voting.getName(), associate.getName(), vote);
    }

}
