package com.domin0x.NBARadars.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository repository;

    public List<Player> getPlayers() {
        return repository.findAll();
    }

    public Page<Player> getPlayers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Player> listPlayersByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Player getPlayerById(int id){
        Optional<Player> optionalPlayer = repository.findById(id);
        return optionalPlayer.orElseThrow(()-> new IllegalArgumentException("No player with id = " + id + " found"));
    }

}
