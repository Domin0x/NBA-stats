package com.domin0x.RESTCalling.repository;

import com.domin0x.RESTCalling.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayersRepository extends JpaRepository<Player, Integer> {

    List<Player> findByNameContainingIgnoreCase(String name);
}
