package com.domin0x.RESTCalling.controllers;

import com.domin0x.RESTCalling.form.PlayerSearchForm;
import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.RadarLayout;
import com.domin0x.RESTCalling.radar.RadarType;
import com.domin0x.RESTCalling.service.PerGameStatsService;
import com.domin0x.RESTCalling.service.PlayerService;
import com.domin0x.RESTCalling.service.RadarWebService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PerGameStatsService perGameStatsService;
    @Autowired
    private RadarWebService radarService;

    @GetMapping({"/all", "/", ""})
    public String listAllPlayers(Model model, @PageableDefault(value=30, page=0, sort = {"name", "id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        List<Player> players = playerService.getPlayers(pageable);
        model.addAttribute("pageInfo", pageable);
        model.addAttribute("players", players);

        return "player/player-list";
    }

//    @GetMapping({"/all/{pageId}", "/{pageId}", "/{pageId}"})
//    public String listAllPlayers(Model model,@PathVariable int pageId) {
//        List<Player> players = playerService.getPlayers(pageId);
//        model.addAttribute("players", players);
//
//        return "player/player-list";
//    }


    @GetMapping({"/search"})
    public String findPlayersByName(Model model) {
        model.addAttribute("playerForm", new PlayerSearchForm());
        return "player/player-search";
    }

    @PostMapping("/search")
    public String findPlayersSubmit(Model model, @ModelAttribute PlayerSearchForm form) {
        List<Player> players = playerService.listPlayersByName(form.getName());
        model.addAttribute("players", players);
        model.addAttribute("searchPhrase`", form.getName());
        return "player/player-list";
    }

    @GetMapping("/{playerId}")
    public String showPlayer(Model model, @PathVariable int playerId) {
        Player player = playerService.getPlayerById(playerId);
        List<PerGameStats> stats =  perGameStatsService.getPerGameStatsForPlayer(player);

        model.addAttribute("player", player);
        model.addAttribute("stats", stats);

        return "player/player-page";
    }

    @RequestMapping(value = "/{playerId}/{season}/{type}", method = RequestMethod.GET)
    public String getRadarData(Model model, @PathVariable int playerId, @PathVariable int season, @PathVariable String type)throws JsonProcessingException {
        RadarType radarType = type.equals("scoring") ? RadarType.SHOOTING_STATS
                                                             : RadarType.PLAYER_BASE_STATS;

        byte [] img = radarService.getRadarImage(radarType,playerService.getPlayerById(playerId), season);
        String encodeBase64 = convertBinImageToString(img);
        model.addAttribute("image", encodeBase64);

        return "player/player-radar";
    }

    public static String convertBinImageToString(byte[] binImage) {
        if(binImage!=null && binImage.length>0) {
            return Base64.getEncoder().encodeToString(binImage);
        }
        else
            return "";
    }

}
