package com.domin0x.NBARadars.main;


import com.domin0x.NBARadars.radar.RadarForm;
import com.domin0x.NBARadars.player.Player;
import com.domin0x.NBARadars.radar.RadarType;
import com.domin0x.NBARadars.stats.PerGameStatsService;
import com.domin0x.NBARadars.player.PlayerService;
import com.domin0x.NBARadars.radar.RadarLayoutService;
import com.domin0x.NBARadars.radar.RadarWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;


@Controller
public class MainController {

    static final RadarType [] types = RadarType.values();
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PerGameStatsService perGameStatsService;
    @Autowired
    private RadarLayoutService radarLayoutService;
    @Autowired
    private RadarWebService radarWebService;

    @GetMapping({"/index", "/", ""})
    public String showMainPage(Model model) {
        model.addAttribute("radarForm", new RadarForm());
        List<Player> players = playerService.getPlayers();
        players.sort(Comparator.comparing(Player::getName));
        model.addAttribute("players", players);
        model.addAttribute("seasons", perGameStatsService.getOrderedSeasonsForPlayer(players.get(0)));
        model.addAttribute("radarTypes", types);

        return "index";
    }

    @GetMapping("/test")
    public ResponseEntity returnOK(){
        return ResponseEntity.ok().build();
    }
}
