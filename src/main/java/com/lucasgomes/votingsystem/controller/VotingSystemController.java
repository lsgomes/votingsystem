package com.lucasgomes.votingsystem.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasgomes.votingsystem.dao.AssociateRepository;
import com.lucasgomes.votingsystem.dao.VotingRepository;
import com.lucasgomes.votingsystem.exceptions.AssociateAlreadyVotedException;
import com.lucasgomes.votingsystem.exceptions.AssociateNotFoundException;
import com.lucasgomes.votingsystem.exceptions.AssociateNotIncludedException;
import com.lucasgomes.votingsystem.exceptions.InvalidVoteException;
import com.lucasgomes.votingsystem.exceptions.VotingAlreadyClosedException;
import com.lucasgomes.votingsystem.exceptions.VotingNotFoundException;
import com.lucasgomes.votingsystem.model.dto.AddAssociateToVotingDto;
import com.lucasgomes.votingsystem.model.dto.VoteDto;
import com.lucasgomes.votingsystem.model.dto.VotingSessionDto;
import com.lucasgomes.votingsystem.model.entity.Associate;
import com.lucasgomes.votingsystem.model.entity.AssociateVote;
import com.lucasgomes.votingsystem.model.entity.Voting;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Lucas
 *
 * 	Spring Boot RestController to handle HTTP requests.
 *
 *	Uses built-in logback logger.
 * 
 *
 */

@RestController
@Slf4j
public class VotingSystemController {

	// Default session duration
	static final Long DEFAULT_VOTING_SESSION_DURATION = 60L;
	
	// If the session duration is equal or less than this, is considered invalid
	static final Long INVALID_VOTING_SESSION_DURATION = 0L ;

	// JPA repository for voting entity
	private final VotingRepository votingRepository;

	// JPA repository for associate entity
	private final AssociateRepository associateRepository;

	// Constructor
	public VotingSystemController(VotingRepository votingRepository, 
								  AssociateRepository associateRepository)
	{
		this.votingRepository = votingRepository;
		this.associateRepository = associateRepository;
	}
	
	// Creates a Voting
	@PostMapping("/voting")
	synchronized Voting createVoting(@RequestBody Voting voting)
	{
		log.info("/voting called, saving: " + voting.toString() );
		return votingRepository.save(voting);
	}
	
	// Gets a Voting
	// If active parameter is specified, returns all active or inactive voting
	@GetMapping("/voting")
	List<Voting> getAllVotings(@RequestParam(value="active", required=false) Boolean active )
	{
		log.info("/voting called, retrieving all votings with active " + active);
		
		List<Voting> votings;
		
		if ( active == null )
		{
			votings = votingRepository.findAll();
		}
		else
		{
			votings = new ArrayList<>();

			for ( Voting votingFromDb : votingRepository.findAll() )
			{
				if ( votingFromDb.getEndTime() != null)
				{
					boolean isVotingClosed = Instant.now().isAfter( votingFromDb.getEndTime() );
					
					if ( !isVotingClosed == active )
					{
						votings.add(votingFromDb);
					}
				}
				// If the voting has never started, endTime is null
				else if ( !active )
				{
					votings.add(votingFromDb);
				}
			}
		}
		
		return votings;
	}

	// Gets a specific voting according to id
	@GetMapping("/voting/{id}")
	Voting getVoting(@PathVariable Long id)
	{
		log.info("/voting/" + id + " called, retrieving voting");

		return votingRepository.findById( id )
				.orElseThrow( () -> new VotingNotFoundException( id ) );
	}
	
	// Creates an Associate
	@PostMapping("/associate")
	synchronized Associate createAssociate(@RequestBody Associate associate)
	{
		log.info("/associate called, saving: " + associate.toString() );
		return associateRepository.save(associate);
	}
	
	// Gets a specific Associate according to id
	@GetMapping("/associate/{id}")
	Associate getAssociate(@PathVariable Long id)
	{
		log.info("/associate/" + id + " called, retrieving associate");

		return associateRepository.findById( id )
				.orElseThrow( () -> new AssociateNotFoundException( id ) );
	}
	
	// Gets all Associates
	@GetMapping("/associate")
	List<Associate> getAllAssociates()
	{
		log.info("/associate called, retrieving all associates");
		return associateRepository.findAll();
	}
	
	// Add an Associate to a Voting
	// This step is necessary for an Associate to be able to Vote in a specific Voting
	@PostMapping("/addassociatetovoting")
	synchronized Associate addAssociateToVoting(@RequestBody AddAssociateToVotingDto addAssociateToVotingDto)
	{
		Long associateId = addAssociateToVotingDto.getAssociateId();
		Long votingId = addAssociateToVotingDto.getVotingId();
		
		log.info("/addassociatetovoting called, for associate id " + associateId + " voting id " + votingId);

		Associate associateFromDb = associateRepository.findById( associateId  )
				.orElseThrow( () -> new AssociateNotFoundException( associateId ) );
		
		Voting votingFromDb = votingRepository.findById( votingId )
				.orElseThrow( () -> new VotingNotFoundException( votingId ) );
		
		AssociateVote associateVote = new AssociateVote(votingFromDb, associateFromDb);
		
		votingFromDb.getAssociateVotes().add(associateVote);
						
		votingRepository.save(votingFromDb);

		return associateFromDb;
	}
	
	// Opens a Voting Session according to specified duration. Default: DEFAULT_VOTING_SESSION_DURATION
	@PostMapping("/votingsession")
	synchronized Voting createVotingSession(@RequestBody VotingSessionDto votingSessionDto)
	{
		Long votingId = votingSessionDto.getVotingId();
		Long duration = votingSessionDto.getDuration();
		
		if (duration == null || duration <= INVALID_VOTING_SESSION_DURATION)
		{
			duration = DEFAULT_VOTING_SESSION_DURATION;
		}
		
		log.info("/votingsession called, for voting id " + votingId + " duration " + duration);

		Voting votingFromDb = votingRepository.findById( votingId )
				.orElseThrow( () -> new VotingNotFoundException( votingId ) );
				
		Instant votingEndTime = Instant.now().plus(duration, ChronoUnit.SECONDS);
		
		votingFromDb.setEndTime(votingEndTime);
		
		votingRepository.save(votingFromDb);
		
		log.info( "Voting id: " + votingId  + " open until: " + votingEndTime.toString() );
		
		return votingFromDb;
	}
	
	// Receive an Associate vote for an active Session
	@PostMapping("/vote")
	synchronized AssociateVote vote(@RequestBody VoteDto voteDto)
	{
		log.info("/vote called");

		Long associateId = voteDto.getAssociateId();
		Long votingId = voteDto.getVotingId();
		Boolean vote = voteDto.getVote();
		
		if ( vote == null )
		{
			throw new InvalidVoteException(vote);
		}
		
		Associate associateFromDb = associateRepository.findById( associateId )
					.orElseThrow( () -> new AssociateNotFoundException( associateId ) );
		
		Voting votingFromDb = votingRepository.findById( votingId )
					.orElseThrow( () -> new VotingNotFoundException( votingId ) );
		
		AssociateVote associateVote = new AssociateVote(votingFromDb, associateFromDb);
		
		boolean isIncludedInVoting = votingFromDb.getAssociateVotes().contains(associateVote);
				
		if ( isIncludedInVoting )
		{
			boolean hasNotVotedYet = associateVote.getVote() == null;
			
			if ( hasNotVotedYet )
			{
				// Associate has not voted yet
				boolean isVotingClosed = Instant.now().isAfter( votingFromDb.getEndTime() );

				// Only accepts the vote if the voting is not closed
				if ( !isVotingClosed )
				{	
					log.info("Associate " + associateId + " voted " + vote + " for voting " + votingId);

					// Set does not allow replace, so remove and add the vote
					votingFromDb.getAssociateVotes().remove(associateVote); 
					
					associateVote.setVote(vote);

					votingFromDb.getAssociateVotes().add(associateVote);
					
					// Increment number of votes
					votingFromDb.setNumberOfVotes( votingFromDb.getNumberOfVotes() + 1 );

					// Save the voting and cascading entities
					votingRepository.save(votingFromDb);
				}
				else
				{
					throw new VotingAlreadyClosedException(votingId);
				}
			}
			else
			{
				throw new AssociateAlreadyVotedException(associateId, votingId);
			}
		}
		else
		{
			throw new AssociateNotIncludedException(associateId, votingId);
		}

		return associateVote;
	}

}
