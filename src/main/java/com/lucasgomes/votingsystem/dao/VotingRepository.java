package com.lucasgomes.votingsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasgomes.votingsystem.model.entity.Voting;

/**
 * @author Lucas
 *
 */
public interface VotingRepository extends JpaRepository<Voting, Long> {

}
