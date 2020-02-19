package com.domin0x.NBARadars.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Pageable p = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
        return repository.findAll(p);
    }

    public Page<Player> listPlayersByName(String name, Pageable pageable) {
        Pageable p = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
        return repository.findByNameContainingIgnoreCase(name, p);
    }

    public Player getPlayerById(int id){
        Optional<Player> optionalPlayer = repository.findById(id);
        return optionalPlayer.orElseThrow(()-> new IllegalArgumentException("No player with id = " + id + " found"));
    }

}
