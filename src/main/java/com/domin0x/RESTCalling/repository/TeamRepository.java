package com.domin0x.RESTCalling.repository;

import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

}
