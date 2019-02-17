package com.lucasgomes.votingsystem.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
 * @author Lucas Gomes
 *
 *         Service which handles RestController requests.
 *         
 *         Spring Boot built-in log is used for logging purposes.
 *         
 */
@Slf4j
@Service
public class VotingSystemService {

	private final VotingRepository votingRepository;
	
	private final AssociateRepository associateRepository;

	/**
	 * Service Constructor
	 * 
	 * @param votingRepository    JPA repository for voting entity
	 * @param associateRepository JPA repository for associate entity
	 */
	public VotingSystemService(VotingRepository votingRepository, AssociateRepository associateRepository) 
	{
		this.votingRepository = votingRepository;
		this.associateRepository = associateRepository;
	}

	// Default session duration
	static final Long DEFAULT_VOTING_SESSION_DURATION = 60L;

	// If the session duration is equal or less than this, is considered invalid
	static final Long INVALID_VOTING_SESSION_DURATION = 0L;

	/**
	 * Creates a Voting
	 * 
	 * @param  voting the Voting to be saved in the repository
	 * @return the saved Voting
	 */
	public synchronized Voting createVoting(Voting voting) 
	{
		log.info("/voting called, saving: " + voting.toString());

		return votingRepository.save(voting);
	}
	
	/**
	 * Gets Voting according to specified id
	 * 
	 * @param  id the desired Voting id
	 * @return the Voting with the specified id  
	 */
	public Voting getVoting(Long id)
	{
		log.info("/voting/" + id + " called, retrieving voting");

		return votingRepository.findById(id).orElseThrow(() -> new VotingNotFoundException(id));
	}

	/**
	 * Gets all Voting
	 *
	 * If active parameter is specified, returns all active or inactive voting.
	 * 
	 * @param  active if true, returns all active voting. If false, returns all inactive voting
	 * @return all Voting from db
	 */
	public List<Voting> getAllVotings(Boolean active) 
	{
		log.info("/voting called, retrieving all votings with active " + active);

		List<Voting> votings;

		if (active == null) 
		{
			votings = votingRepository.findAll();
		} 
		else 
		{
			votings = new ArrayList<>();

			for (Voting votingFromDb : votingRepository.findAll()) 
			{
				if (votingFromDb.getEndTime() != null) 
				{
					boolean isVotingClosed = Instant.now().isAfter(votingFromDb.getEndTime());

					if (!isVotingClosed == active) 
					{
						votings.add(votingFromDb);
					}
				}
				// If the voting never started, endTime is null
				else if (!active) 
				{
					votings.add(votingFromDb);
				}
			}
		}

		return votings;
	}
	
	/**
	 * Creates an Associate
	 *
	 * @param  associate the Associate to be saved in the repository
	 * @return the saved Associate
	 */
	public synchronized Associate createAssociate(Associate associate) 
	{
		log.info("/associate called, saving: " + associate.toString());

		return associateRepository.save(associate);
	}

	/**
	 * Gets Associate according to specified id
	 * 
	 * @param  id the desired Associate id
	 * @return the Associate with the specified id  
	 */
	public Associate getAssociate(Long id) 
	{
		log.info("/associate/" + id + " called, retrieving associate");

		return associateRepository.findById(id).orElseThrow(() -> new AssociateNotFoundException(id));
	}
	
	/**
	 * Gets all Associates
	 * 
	 * @return all Associates from db
	 */
	public List<Associate> getAllAssociates() 
	{
		log.info("/associate called, retrieving all associates");

		return associateRepository.findAll();
	}
	
	/**
	 * Adds an Associate to a Voting
	 * 
	 * This step is necessary for an Associate to be able to Vote in a specific Voting
	 * 
	 * @param  addAssociateToVotingDto containing votingId and associateId
	 * @return the Associate from db
	 */
	public synchronized Associate addAssociateToVoting(AddAssociateToVotingDto addAssociateToVotingDto) 
	{
		Long associateId = addAssociateToVotingDto.getAssociateId();
		Long votingId = addAssociateToVotingDto.getVotingId();

		log.info("/addassociatetovoting called, for associate id " + associateId + " voting id " + votingId);

		Associate associateFromDb = associateRepository.findById(associateId)
				.orElseThrow(() -> new AssociateNotFoundException(associateId));

		Voting votingFromDb = votingRepository.findById(votingId)
				.orElseThrow(() -> new VotingNotFoundException(votingId));

		AssociateVote associateVote = new AssociateVote(votingFromDb, associateFromDb);

		votingFromDb.getAssociateVotes().add(associateVote);

		votingRepository.save(votingFromDb);

		return associateFromDb;
	}
	
	/**
	 * Opens a Voting Session according to specified duration
	 * 
	 * @param  votingSessionDto containing votingId and duration. If duration is omitted, defaults to DEFAULT_VOTING_SESSION_DURATION
	 * @return the Voting from db
	 */
	public synchronized Voting createVotingSession(VotingSessionDto votingSessionDto) 
	{
		Long votingId = votingSessionDto.getVotingId();
		Long duration = votingSessionDto.getDuration();

		if (duration == null || duration <= INVALID_VOTING_SESSION_DURATION) 
		{
			duration = DEFAULT_VOTING_SESSION_DURATION;
		}

		log.info("/votingsession called, for voting id " + votingId + " duration " + duration);

		Voting votingFromDb = votingRepository.findById(votingId)
				.orElseThrow(() -> new VotingNotFoundException(votingId));

		Instant votingEndTime = Instant.now().plus(duration, ChronoUnit.SECONDS);

		votingFromDb.setEndTime(votingEndTime);

		votingRepository.save(votingFromDb);

		log.info("Voting id: " + votingId + " open until: " + votingEndTime.toString());

		return votingFromDb;
	}
	
	/**
	 * Receive an Associate Vote for an active Vote Session
	 *
	 * @param  voteDto containing associateId, votingId, vote
	 * @return the Associate Vote
	 */
	public synchronized AssociateVote vote(VoteDto voteDto) 
	{
		log.info("/vote called");

		Long associateId = voteDto.getAssociateId();
		Long votingId = voteDto.getVotingId();
		Boolean vote = voteDto.getVote();

		if (vote == null) 
		{
			throw new InvalidVoteException(vote);
		}

		Associate associateFromDb = associateRepository.findById(associateId)
				.orElseThrow(() -> new AssociateNotFoundException(associateId));

		Voting votingFromDb = votingRepository.findById(votingId)
				.orElseThrow(() -> new VotingNotFoundException(votingId));

		AssociateVote associateVote = new AssociateVote(votingFromDb, associateFromDb);

		boolean isIncludedInVoting = votingFromDb.getAssociateVotes().contains(associateVote);

		if (isIncludedInVoting) 
		{
			boolean hasNotVotedYet = associateVote.getVote() == null;

			if (hasNotVotedYet) 
			{
				// Associate has not voted yet
				boolean isVotingClosed = Instant.now().isAfter(votingFromDb.getEndTime());

				// Only accepts the vote if the voting is not closed
				if (!isVotingClosed) 
				{
					log.info("Associate " + associateId + " voted " + vote + " for voting " + votingId);

					// Set does not allow replace, so remove and add the vote
					votingFromDb.getAssociateVotes().remove(associateVote);

					associateVote.setVote(vote);

					votingFromDb.getAssociateVotes().add(associateVote);

					// Increment number of votes
					votingFromDb.setNumberOfVotes(votingFromDb.getNumberOfVotes() + 1);

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
