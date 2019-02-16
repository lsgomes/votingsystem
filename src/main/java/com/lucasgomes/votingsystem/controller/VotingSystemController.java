package com.lucasgomes.votingsystem.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lucasgomes.votingsystem.dao.AssociateRepository;
import com.lucasgomes.votingsystem.dao.VotingRepository;
import com.lucasgomes.votingsystem.exceptions.AssociateNotFoundException;
import com.lucasgomes.votingsystem.exceptions.VotingNotFoundException;
import com.lucasgomes.votingsystem.model.Associate;
import com.lucasgomes.votingsystem.model.Message;
import com.lucasgomes.votingsystem.model.Voting;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Lucas
 *
 */

@RestController
@Slf4j
public class VotingSystemController {

	private final VotingRepository votingRepository;
	private final AssociateRepository associateRepository;
	
	public VotingSystemController(VotingRepository votingRepository, AssociateRepository associateRepository)
	{
		this.votingRepository = votingRepository;
		this.associateRepository = associateRepository;
	}
	
	@PostMapping("/voting")
	Voting createVoting(@RequestBody Voting voting)
	{
		return votingRepository.save(voting);
	}
	
	@PostMapping("/associate")
	Associate createAssociate(long votingId)
	{
		Associate associate = new Associate(votingId);

		return associateRepository.save(associate);
	}
	
	@PostMapping("/addassociatetovoting")
	Associate addAssociateToVoting(long associateId, long votingId)
	{
		Associate associateFromDb = associateRepository.findById( associateId )
				.orElseThrow( () -> new AssociateNotFoundException( associateId ) );
		
		Voting votingFromDb = votingRepository.findById( votingId )
				.orElseThrow( () -> new VotingNotFoundException( votingId ) );
		
		Map<Long, Boolean> associateVotingMap = associateFromDb.getVotingMap();
		
		associateVotingMap.put(votingFromDb.getId(), null);
		
		votingFromDb.addOrReplaceAssociate(associateFromDb, null);

		votingRepository.save(votingFromDb);
		associateRepository.save(associateFromDb);

		return null;
	}
	
	@PostMapping("/votingsession")
	Voting createVotingSession(long id, long duration)
	{
		Voting votingFromDb = votingRepository.findById( id )
				.orElseThrow( () -> new VotingNotFoundException( id ) );
		
		long votingDuration = duration == 0 ? 60 : duration;
		
		Instant votingEndTime = Instant.now().plus(votingDuration, ChronoUnit.SECONDS);
		
		votingFromDb.setEndTime( votingEndTime );
		
		votingRepository.save(votingFromDb);
		
		log.info( "Voting id: " + id + " open until: " + votingEndTime.toString() );
		
		return votingFromDb;
	}
	
	@PostMapping("/vote")
	String vote(long associateId, long votingId, boolean vote)
	{
		Associate associateFromDb = associateRepository.findById( associateId )
					.orElseThrow( () -> new AssociateNotFoundException( associateId ) );
		
		Voting votingFromDb = votingRepository.findById( votingId )
					.orElseThrow( () -> new VotingNotFoundException( votingId ) );
		
		String result = "Associate: " + associateId;
		
		if ( associateFromDb.hasVotingId( votingId ) )
		{
			if ( !associateFromDb.hasAlreadyVoted( votingId ) )
			{
				boolean isVotingClosed = Instant.now().isAfter( votingFromDb.getEndTime() );
				
				if ( !isVotingClosed )
				{
					associateFromDb.setVote(votingFromDb.getId(), vote);
					
					votingFromDb.addOrReplaceAssociate(associateFromDb, vote);
					
					if ( vote )
					{
						votingFromDb.addVote();
					}
					
					votingRepository.save(votingFromDb);
					
					result = " participated in voting " + votingId + " and voted " + vote;

					associateRepository.save(associateFromDb);
				}
				else
				{
					// exception?
				}
			}
			else
			{
				// exception
			}
		}
		else
		{
			// exception
		}
				
		return result;
	}
	
	@GetMapping("/countvotes")
	Voting countVotes(long votingId)
	{
		Voting votingFromDb = votingRepository.findById( votingId )
				.orElseThrow( () -> new VotingNotFoundException( votingId ) );
		
		return votingFromDb;		
	}

}
