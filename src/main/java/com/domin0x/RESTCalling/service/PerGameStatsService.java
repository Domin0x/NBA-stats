package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.model.Team;
import com.domin0x.RESTCalling.radar.StatType;
import com.domin0x.RESTCalling.repository.PerGameStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerGameStatsService {
/*   player statlines are associated with PerGameStatsId consisting of 3 columns: player_id, season, team_id
     in case a player played for more than 1 team during single season(e.g he was traded mid-season)
     the team_id column will be filled with the id of a special team instance identified by abbreviation "TOT"
     this follows the default design of BasketballReference.com, which is the source of data used in this project*/
    private final static String MULTIPLE_TEAMS_ABBREVIATION = "TOT";

    @Autowired
    private PerGameStatsRepository repository;

    public void add(PerGameStats statline) {
        repository.save(statline);
    }

    public List<PerGameStats> getPerGameStats() {
        return repository.findAll();
    }

    public PerGameStats getPerGameStatsById(Player player, Team team, int season){
        return repository.findByIdPlayerAndIdTeamAndIdSeason(player,team,season);
    }

    public PerGameStats getPerGameStatsById(Player player, int season){
        List<PerGameStats> statsList = repository.findByIdPlayerAndIdSeason(player, season);
        if (statsList.size() == 0)
            return null;
        if (statsList.size() == 1)
            return statsList.get(0);

        //if a player played for multiple teams in a single season find aggregated stats
        return statsList.stream()
                .filter(x -> x.getId().getTeam().getAbbreviation().equals(MULTIPLE_TEAMS_ABBREVIATION))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple elements: " + a + ", " + b);
                })
                .orElse(null);

    }

    public List<PerGameStats> getPerGameStatsForPlayer(Player player){
        return repository.findByIdPlayer(player);
    }

    public List<Integer> getSeasonsForPlayer(Player player){
        return repository.findByIdPlayer(player).stream()
                .map(stats -> stats.getId().getSeason())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public BigDecimal getMaxNoOfPts(){return repository.findMaxAmountOfPoints();}
    public Integer getMaxSeason(){return repository.findMaxSeason();}
    public Integer getMinSeason(){return repository.findMinSeason();}
    public List <Integer> findAllSeasons(){return repository.findAllSeasons();}
    public BigDecimal getQualifiedMaxOfField(StatType statType){return repository.findQualifiedMaxAmountOfField(statType);}
    public BigDecimal getQualifiedMinOfField(StatType statType){return repository.findQualifiedMinAmountOfField(statType);}

}
