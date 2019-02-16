package com.lucasgomes.votingsystem.dao;

import com.lucasgomes.votingsystem.model.Voting;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lucas
 *
 */
public interface VotingRepository extends JpaRepository<Voting, Long> {

}
