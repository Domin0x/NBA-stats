package com.domin0x.RESTCalling.controllers;

import com.domin0x.RESTCalling.form.PlayerSearchForm;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.RadarType;
import com.domin0x.RESTCalling.service.PerGameStatsService;
import com.domin0x.RESTCalling.service.PlayerService;
import com.domin0x.RESTCalling.service.RadarWebService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String listAllPlayers(Model model, @PageableDefault(value = 20, page = 0, sort = {"name", "id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Player> playerPages = playerService.getPlayers(pageable);
        model.addAttribute("playerPages", playerPages);

        List<Integer> pageNumbers = getPageNumbersList(playerPages);
        model.addAttribute("pageNumbers", pageNumbers);

        return "player/player-list";
    }

    @GetMapping({"/search"})
    public String findPlayersByName(Model model) {
        model.addAttribute("playerForm", new PlayerSearchForm());
        return "player/player-search";
    }

    @GetMapping("/search_result")
    public String findPlayersSubmit(Model model,
                                    @ModelAttribute PlayerSearchForm form,
                                    @PageableDefault(value=20, sort = {"name"}, direction = Sort.Direction.ASC)
                                        Pageable pageable) {
        String searchPhrase = form.getName();
        Page<Player> playerPages = playerService.listPlayersByName(searchPhrase, pageable);
        model.addAttribute("pageNumbers", getPageNumbersList(playerPages));

        try{
            String searchPhraseEncoded = URLEncoder.encode(searchPhrase, "UTF-8");
            model.addAttribute("searchPhraseEncoded", searchPhraseEncoded);
        }catch (UnsupportedEncodingException e){
            throw new AssertionError("UTF-8 not supported");//should never happen(?)
        }

        model.addAttribute("playerPages", playerPages);
        model.addAttribute("searchPhrase", searchPhrase);
        return "player/player-list";
    }

    @GetMapping("/{playerId}")
    public String showPlayer(Model model, @PathVariable int playerId) {
        Player player = playerService.getPlayerById(playerId);
        model.addAttribute("player", player);
        model.addAttribute("stats", perGameStatsService.getPerGameStatsForPlayer(player));
        model.addAttribute("radarTypes", RadarType.values());

        return "player/player-page";
    }

    @RequestMapping(value = "/{playerId}/{season}/{type}", method = RequestMethod.GET)
    public String getRadarData(Model model, @PathVariable int playerId, @PathVariable int season, @PathVariable String type) throws JsonProcessingException {
        RadarType radarType = RadarType.fromString(type);
        String base64Image = radarService.getRadarImage(radarType,playerService.getPlayerById(playerId), season);
        model.addAttribute("image", base64Image);

        return "player/player-radar";
    }

    private List<Integer> getPageNumbersList(Page pages) {
        int totalPages = pages.getTotalPages();
        if (totalPages == 1)
            return Collections.singletonList(1);
        return IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
    }

}
