package com.domin0x.NBARadars.player;

import com.domin0x.NBARadars.radar.RadarLayoutService;
import com.domin0x.NBARadars.radar.file.RadarFileService;
import com.domin0x.NBARadars.stats.pergame.PerGameStatsService;
import com.domin0x.NBARadars.team.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlayerController playerController;
    @MockBean
    private PlayerService playerService;
    @MockBean
    private TeamService teamService;
    @MockBean
    private PerGameStatsService perGameStatsService;
    @MockBean
    private RadarLayoutService radarLayoutService;
    @MockBean
    private RadarFileService radarFileService;

    @Test
    public void showPlayersList() throws Exception {

        Player p = new Player();
        Page<Player> pageWithOnePlayer = new PageImpl<>(List.of(p));
        Pageable pageRequest = PageRequest.of(0, 20, Sort.Direction.ASC, "name", "id");
        Mockito.when(playerService.getPlayers(pageRequest))
                .thenReturn(pageWithOnePlayer);

        mockMvc.perform(get("/players/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("player/player-list"))
                .andExpect(model().attributeExists("pageNumbers", "playerPages")
                );
    }
}
