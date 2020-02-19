package com.domin0x.NBARadars.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

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
