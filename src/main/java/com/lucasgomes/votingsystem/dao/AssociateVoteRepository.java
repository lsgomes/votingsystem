package com.lucasgomes.votingsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasgomes.votingsystem.model.AssociateVote;
import com.lucasgomes.votingsystem.model.AssociateVoteKey;

/**
 * @author Lucas
 *
 */
public interface AssociateVoteRepository extends JpaRepository<AssociateVote, AssociateVoteKey> {

}
