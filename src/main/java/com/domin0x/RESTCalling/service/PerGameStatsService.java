package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.model.Team;
import com.domin0x.RESTCalling.repository.PerGameStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PerGameStatsService {

    @Autowired PerGameStatsRepository repository;

    public void add(PerGameStats statline) {
        repository.save(statline);
    }

    public List<PerGameStats> getPerGameStats() {
        return repository.findAll();
    }

    public PerGameStats getPerGameStatsById(Player player, int season){
        return repository.findByIdPlayerAndIdSeason(player, season);
    }

    public List<PerGameStats> getPerGameStatsForPlayer(Player player){
        return repository.findByIdPlayer(player);
    }

    public BigDecimal getMaxNoOfPts(){return repository.findMaxAmountOfPoints();}

}
