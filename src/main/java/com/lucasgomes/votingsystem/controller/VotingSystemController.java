package com.lucasgomes.votingsystem.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lucasgomes.votingsystem.dao.AssociateRepository;
import com.lucasgomes.votingsystem.dao.AssociateVoteRepository;
import com.lucasgomes.votingsystem.dao.VotingRepository;
import com.lucasgomes.votingsystem.exceptions.AssociateAlreadyVotedException;
import com.lucasgomes.votingsystem.exceptions.AssociateNotFoundException;
import com.lucasgomes.votingsystem.exceptions.AssociateNotIncludedException;
import com.lucasgomes.votingsystem.exceptions.InvalidVoteException;
import com.lucasgomes.votingsystem.exceptions.VotingAlreadyClosedException;
import com.lucasgomes.votingsystem.exceptions.VotingNotFoundException;
import com.lucasgomes.votingsystem.model.dto.AddAssociateToVotingDto;
import com.lucasgomes.votingsystem.model.dto.CountVotesDto;
import com.lucasgomes.votingsystem.model.dto.VoteDto;
import com.lucasgomes.votingsystem.model.dto.VotingSessionDto;
import com.lucasgomes.votingsystem.model.entity.Associate;
import com.lucasgomes.votingsystem.model.entity.AssociateVote;
import com.lucasgomes.votingsystem.model.entity.AssociateVoteKey;
import com.lucasgomes.votingsystem.model.entity.Voting;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Lucas
 *
 */

@RestController
@Slf4j
public class VotingSystemController {

	Logger logger = LoggerFactory.getLogger(VotingSystemController.class);
	
	static final Long DEFAULT_VOTING_SESSION_DURATION = 60L;
	static final Long INVALID_VOTING_SESSION_DURATION = 0L ;
	
	private final VotingRepository votingRepository;

	private final AssociateRepository associateRepository;
	
	private final AssociateVoteRepository associateVoteRepository;

	public VotingSystemController(VotingRepository votingRepository, 
								  AssociateRepository associateRepository,
								  AssociateVoteRepository associateVoteRepository)
	{
		this.votingRepository = votingRepository;
		this.associateRepository = associateRepository;
		this.associateVoteRepository = associateVoteRepository;
	}
	
	@PostMapping("/voting")
	Voting createVoting(@RequestBody Voting voting)
	{
		logger.info("/voting called, saving: " + voting.toString() );
		return votingRepository.save(voting);
	}
	
	@GetMapping("/voting")
	List<Voting> getAllVotings()
	{
		logger.info("/voting called, retrieving all votings");
		return votingRepository.findAll();
	}
	
	@GetMapping("/voting/{id}")
	Voting getVoting(@PathVariable Long id)
	{
		logger.info("/voting/" + id + " called, retrieving voting");

		return votingRepository.findById( id )
				.orElseThrow( () -> new VotingNotFoundException( id ) );
	}
	
	@PostMapping("/associate")
	Associate createAssociate(@RequestBody Associate associate)
	{
		logger.info("/associate called, saving: " + associate.toString() );
		return associateRepository.save(associate);
	}
	
	@GetMapping("/associate/{id}")
	Associate getAssociate(@PathVariable Long id)
	{
		logger.info("/associate/" + id + " called, retrieving associate");

		return associateRepository.findById( id )
				.orElseThrow( () -> new AssociateNotFoundException( id ) );
	}
	
	@GetMapping("/associate")
	List<Associate> getAllAssociates()
	{
		logger.info("/associate called, retrieving all associates");
		return associateRepository.findAll();
	}
	
	@PostMapping("/addassociatetovoting")
	Associate addAssociateToVoting(@RequestBody AddAssociateToVotingDto addAssociateToVotingDto)
	{
		Long associateId = addAssociateToVotingDto.getAssociateId();
		Long votingId = addAssociateToVotingDto.getVotingId();
		
		logger.info("/addassociatetovoting called, for associate id " + associateId + " voting id " + votingId);

		Associate associateFromDb = associateRepository.findById( associateId  )
				.orElseThrow( () -> new AssociateNotFoundException( associateId ) );
		
		Voting votingFromDb = votingRepository.findById( votingId )
				.orElseThrow( () -> new VotingNotFoundException( votingId ) );
		
		votingFromDb.getAssociateList().add(associateFromDb);
		
		associateFromDb.getVotingList().add(votingFromDb);
				
		votingRepository.save(votingFromDb);
		
		associateRepository.save(associateFromDb);

		return associateFromDb;
	}
	
	@PostMapping("/votingsession")
	Voting createVotingSession(@RequestBody VotingSessionDto votingSessionDto)
	{
		Long votingId = votingSessionDto.getVotingId();
		Long duration = votingSessionDto.getDuration();
		
		if (duration == null || duration <= INVALID_VOTING_SESSION_DURATION)
		{
			duration = DEFAULT_VOTING_SESSION_DURATION;
		}
		
		logger.info("/votingsession called, for voting id " + votingId + " duration " + duration);

		Voting votingFromDb = votingRepository.findById( votingId )
				.orElseThrow( () -> new VotingNotFoundException( votingId ) );
				
		Instant votingEndTime = Instant.now().plus(duration, ChronoUnit.SECONDS);
		
		votingFromDb.setEndTime(votingEndTime);
		
		votingRepository.save(votingFromDb);
		
		logger.info( "Voting id: " + votingId  + " open until: " + votingEndTime.toString() );
		
		return votingFromDb;
	}
	
	@PostMapping("/vote")
	AssociateVote vote(@RequestBody VoteDto voteDto)
	{
		logger.info("/vote called");

		Long associateId = voteDto.getAssociateId();
		Long votingId = voteDto.getVotingId();
		Boolean vote = voteDto.getVote();
		
		Associate associateFromDb = associateRepository.findById( associateId )
					.orElseThrow( () -> new AssociateNotFoundException( associateId ) );
		
		Voting votingFromDb = votingRepository.findById( votingId )
					.orElseThrow( () -> new VotingNotFoundException( votingId ) );
		
		if ( vote == null )
		{
			throw new InvalidVoteException(vote);
		}
		
		AssociateVote associateVote = new AssociateVote(votingFromDb, associateFromDb, vote );
		
		boolean isAssociateIncludedInVoting = votingFromDb.getAssociateList().contains(associateFromDb);
		
		if ( isAssociateIncludedInVoting )
		{
			Optional<AssociateVote> hasAlreadyVoted = associateVoteRepository.findById( new AssociateVoteKey(votingId, associateId) );
			
			// Associate has not voted yet
			if ( !hasAlreadyVoted.isPresent() )
			{
				boolean isVotingClosed = Instant.now().isAfter( votingFromDb.getEndTime() );

				if ( !isVotingClosed )
				{	
					logger.info("Associate " + associateId + " voted " + vote + " for voting " + votingId);
					associateVoteRepository.save(associateVote);
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
	
	@GetMapping("/vote")
	List<AssociateVote> getAllVotes()
	{
		logger.info("/vote called, retrieving all votes");
		return associateVoteRepository.findAll();
	}
	
	@GetMapping("/countvotes")
	Voting countVotes(@RequestBody CountVotesDto countVotesDto)
	{
		Long votingId = countVotesDto.getVotingId();
		
		logger.info("/countvotes called, for voting id " + votingId);
		
		Voting votingFromDb = votingRepository.findById( votingId )
				.orElseThrow( () -> new VotingNotFoundException( votingId ) );
		
		return votingFromDb;		
	}

}
