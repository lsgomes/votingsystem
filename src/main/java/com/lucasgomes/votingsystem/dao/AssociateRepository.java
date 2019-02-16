package com.lucasgomes.votingsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasgomes.votingsystem.model.Associate;

/**
 * @author Lucas
 *
 */
public interface AssociateRepository extends JpaRepository<Associate, Long> {

}