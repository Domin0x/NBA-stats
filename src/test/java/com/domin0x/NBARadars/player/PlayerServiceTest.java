package com.domin0x.NBARadars.player;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PlayerService.class})
public class PlayerServiceTest {
    final Integer EXISTING_ID = 1;
    final Integer NONEXISTING_ID = -1;

    @Autowired
    PlayerService playerService;

    @MockBean
    PlayerRepository playerRepository;


    @Test
    public void whenExistingId_thenPlayerShouldBeFound(){
        Player p = new Player();
        p.setId(EXISTING_ID);

        Mockito.when(playerRepository.findById(EXISTING_ID)).thenReturn(Optional.of(p));
        Player obtainedPlayer = playerService.getPlayerById(EXISTING_ID);
        Assert.assertEquals(EXISTING_ID, obtainedPlayer.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNotExistingId_thenExceptionShouldBeThrown(){
        Mockito.when(playerRepository.findById(NONEXISTING_ID)).thenReturn(Optional.ofNullable(null));
        Player obtainedPlayer = playerService.getPlayerById(NONEXISTING_ID);
    }

    @Test
    public void whenGetPlayersPageable_thenShouldCallFindMethodWithPageableArg(){
        Mockito.when(playerRepository.findAll(isA(Pageable.class))).thenReturn(any(Page.class));
        playerService.getPlayers(PageRequest.of(1,1));

        verify(playerRepository).findAll(isA(Pageable.class));
    }

    @Test
    public void whenListPlayersByName_thenShouldCallFindMethodWithPageableArgAndNameArg(){
        Pageable pageableStub = Mockito.mock(Pageable.class);
        Page<Player> pageStub = Mockito.mock(Page.class);
        String name = "xxx";

        Mockito.when(playerRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(pageStub);

        Page<Player> result = playerService.listPlayersByName(name, pageableStub);

        assertSame(pageStub, result);
    }
}
