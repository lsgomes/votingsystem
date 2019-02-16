package com.lucasgomes.votingsystem.dao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
class CreateDatabase {

	@Bean
	CommandLineRunner initDatabase(VotingRepository repository) {
		return args -> {
			log.info( "Initializing VotingRepository");
			//log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
			//log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
		};
	}
	
	@Bean
	CommandLineRunner initDatabase(AssociateRepository repository) {
		return args -> {
			log.info( "Initializing AssociateRepository");
			//log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
			//log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
		};
	}

}