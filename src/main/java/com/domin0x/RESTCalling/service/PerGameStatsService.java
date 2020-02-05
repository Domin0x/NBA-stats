package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.StatType;
import com.domin0x.RESTCalling.repository.PerGameStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    public PerGameStats getPerGameStatsById(Player player, int season){
        List<PerGameStats> statsList = repository.findByIdPlayerAndIdSeason(player, season);
        //if a player played for multiple teams in a single season find aggregated stats
        if (statsList.size() > 1){
            return statsList.stream()
                    .filter(x -> x.getId().getTeam().getAbbreviation().equals(MULTIPLE_TEAMS_ABBREVIATION))
                    .reduce((a, b) -> {
                        throw new IllegalStateException("Multiple elements: " + a + ", " + b);
                    })
                    .orElse(null);
        }
        return statsList.get(0);
    }

    public List<PerGameStats> getPerGameStatsForPlayer(Player player){
        return repository.findByIdPlayer(player);
    }

    public BigDecimal getMaxNoOfPts(){return repository.findMaxAmountOfPoints();}
    public BigDecimal getQualifiedMaxOfField(StatType statType){return repository.findQualifiedMaxAmountOfField(statType);}
    public BigDecimal getQualifiedMinOfField(StatType statType){return repository.findQualifiedMinAmountOfField(statType);}

}
