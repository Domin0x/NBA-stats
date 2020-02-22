package com.domin0x.NBARadars.team;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TeamService.class})
public class TeamServiceTest {

    final Integer EXISTING_ID = 1;
    final Integer NONEXISTING_ID = -1;

    @Autowired
    TeamService teamService;

    @MockBean
    TeamRepository teamRepository;

    @Test
    public void whenGivenExistingId_thenTeamShouldBeFound(){
        Team team = new Team();
        team.setId(EXISTING_ID);

        Mockito.when(teamRepository.findById(EXISTING_ID)).thenReturn(Optional.of(team));
        Team obtainedTeam = teamService.getTeamById(EXISTING_ID);
        Assert.assertEquals(EXISTING_ID, obtainedTeam.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNotExistingId_thenExceptionShouldBeThrown(){
        Mockito.when(teamRepository.findById(NONEXISTING_ID)).thenReturn(Optional.ofNullable(null));
        Team obtainedTeam = teamService.getTeamById(NONEXISTING_ID);
    }
    
    
}
