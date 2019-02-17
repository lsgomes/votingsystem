package com.lucasgomes.votingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasgomes.votingsystem.model.dto.AddAssociateToVotingDto;
import com.lucasgomes.votingsystem.model.dto.VoteDto;
import com.lucasgomes.votingsystem.model.dto.VotingSessionDto;
import com.lucasgomes.votingsystem.model.entity.Associate;
import com.lucasgomes.votingsystem.model.entity.AssociateVote;
import com.lucasgomes.votingsystem.model.entity.Voting;
import com.lucasgomes.votingsystem.service.VotingSystemService;

import lombok.NoArgsConstructor;

/**
 * @author Lucas Gomes
 *
 *         Spring Boot RestController to handle HTTP requests through a Service.
 *         
 */

@RestController
@NoArgsConstructor
public class VotingSystemController {

	@Autowired
	VotingSystemService votingSystemService;

	@PostMapping("/voting")
	public Voting createVoting(@RequestBody Voting voting) 
	{
		return votingSystemService.createVoting(voting);
	}

	@GetMapping("/voting")
	List<Voting> getAllVotings(@RequestParam(value = "active", required = false) Boolean active) 
	{
		return votingSystemService.getAllVotings(active);
	}

	@GetMapping("/voting/{id}")
	Voting getVoting(@PathVariable Long id) 
	{
		return votingSystemService.getVoting(id);
	}

	@PostMapping("/associate")
	Associate createAssociate(@RequestBody Associate associate) 
	{
		return votingSystemService.createAssociate(associate);
	}

	@GetMapping("/associate/{id}")
	Associate getAssociate(@PathVariable Long id) 
	{
		return votingSystemService.getAssociate(id);
	}

	@GetMapping("/associate")
	List<Associate> getAllAssociates() 
	{
		return votingSystemService.getAllAssociates();
	}

	@PostMapping("/addassociatetovoting")
	Associate addAssociateToVoting(@RequestBody AddAssociateToVotingDto addAssociateToVotingDto) 
	{
		return votingSystemService.addAssociateToVoting(addAssociateToVotingDto);
	}

	@PostMapping("/votingsession")
	Voting createVotingSession(@RequestBody VotingSessionDto votingSessionDto)
	{
		return votingSystemService.createVotingSession(votingSessionDto);
	}

	@PostMapping("/vote")
	AssociateVote vote(@RequestBody VoteDto voteDto) 
	{
		return votingSystemService.vote(voteDto);
	}

}
