package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.repository.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired PlayersRepository repository;

    public void add(Player player) {
        repository.save(player);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<Player> getPlayers() {
        return (List<Player>) repository.findAll();
    }

//    public List<Player> getPlayers(Integer pageNo, Integer pageSize) {
//        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));
//        Page<Player> pagedResult = repository.findAll(paging);
//
//        if (pagedResult.hasContent()) {
//            return pagedResult.getContent();
//        } else {
//            return new ArrayList<Player>();
//        }
//    }

    public List<Player> getPlayers(Pageable pageable) {
        Pageable p = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
        Page<Player> pagedResult = repository.findAll(p);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Player>();
        }
    }

    public List<Player> listPlayersByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public Player getPlayerById(int id){
        Optional<Player> optionalPlayer = repository.findById(id);
        return optionalPlayer.orElseThrow(()-> new IllegalArgumentException("No player with id = " + id + " found"));
    }

}
