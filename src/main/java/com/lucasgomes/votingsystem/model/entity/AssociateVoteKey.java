package com.lucasgomes.votingsystem.model.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.NoArgsConstructor;

/**
 * @author Lucas Gomes
 * 
 * AssociateVoteKey composite key mapping
 *
 */

@Embeddable
@NoArgsConstructor
public class AssociateVoteKey implements Serializable {

	Voting voting;
	
	Associate associate;
	
	public AssociateVoteKey(Voting voting, Associate associate) 
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
               Objects.equals(associate.getName(), that.associate.getName());
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(voting.getName(), associate.getName());
    }

}
