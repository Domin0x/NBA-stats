package com.domin0x.NBARadars;

import com.domin0x.NBARadars.team.Team;
import com.domin0x.NBARadars.team.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableAsync
public class NBARadarsApplication {

	private static final Logger logger = LoggerFactory.getLogger(NBARadarsApplication.class);

	@Bean(name="processExecutor")
	public TaskExecutor workExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setThreadNamePrefix("Async-");
		threadPoolTaskExecutor.setCorePoolSize(3);
		threadPoolTaskExecutor.setMaxPoolSize(3);
		threadPoolTaskExecutor.setQueueCapacity(600);
		threadPoolTaskExecutor.afterPropertiesSet();
		logger.info("ThreadPoolTaskExecutor set");
		return threadPoolTaskExecutor;
	}


	public static void main(String[] args) {
		SpringApplication.run(NBARadarsApplication.class, args);
	}

	@Service
	public static class TeamService {

		@Autowired
		TeamRepository repository;

		public void add(Team Team) {
			repository.save(Team);
		}

		public void delete(int id) {
			repository.deleteById(id);
		}

		public List<Team> getTeams() {
			return repository.findAll();
		}

		public Team getTeamById(int id){
			Optional<Team> optionalTeam = repository.findById(id);
			return optionalTeam.orElseThrow(()-> new IllegalArgumentException("No Team with id = " + id + " found"));
		}

	}
}
