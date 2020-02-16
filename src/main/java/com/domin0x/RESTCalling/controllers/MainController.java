package com.domin0x.RESTCalling.controllers;


import com.domin0x.RESTCalling.form.RadarForm;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.service.PerGameStatsService;
import com.domin0x.RESTCalling.service.PlayerService;
import com.domin0x.RESTCalling.service.RadarLayoutService;
import com.domin0x.RESTCalling.service.RadarWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class MainController {

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
        model.addAttribute("players", players);
        model.addAttribute("seasons", perGameStatsService.getOrderedSeasonsForPlayer(players.get(0)));

        return "index";
    }


}
