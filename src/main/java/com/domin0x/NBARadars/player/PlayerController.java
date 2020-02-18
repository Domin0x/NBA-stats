package com.domin0x.NBARadars.player;

import com.domin0x.NBARadars.NBARadarsApplication;
import com.domin0x.NBARadars.amazon.AmazonService;
import com.domin0x.NBARadars.radar.file.RadarFileService;
import com.domin0x.NBARadars.stats.PerGameStats;
import com.domin0x.NBARadars.stats.PerGameStatsService;
import com.domin0x.NBARadars.radar.*;
import com.domin0x.NBARadars.team.Team;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private NBARadarsApplication.TeamService teamService;
    @Autowired
    private PerGameStatsService perGameStatsService;
    @Autowired
    private RadarWebService radarWebService;
    @Autowired
    private RadarLayoutService radarLayoutService;
    @Autowired
    private AmazonService amazonService;
    @Autowired
    private RadarFileService radarFileService;


    @GetMapping({"/all", "/", ""})
    public String listAllPlayers(Model model, @PageableDefault(value = 20, page = 0, sort = {"name", "id"},
                                                               direction = Sort.Direction.ASC) Pageable pageable) {
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
                                    @PageableDefault(value=20, sort = {"name"},
                                                     direction = Sort.Direction.ASC)
                                                     Pageable pageable) {
        String searchPhrase = form.getSearchPhrase();
        Page<Player> playerPages = playerService.listPlayersByName(searchPhrase, pageable);
        model.addAttribute("pageNumbers", getPageNumbersList(playerPages));

        String searchPhraseEncoded = URLEncoder.encode(searchPhrase, StandardCharsets.UTF_8);
        model.addAttribute("searchPhraseEncoded", searchPhraseEncoded);

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

    @RequestMapping(value = "/{playerId}/{season}/{teamId}/{type}", method = RequestMethod.GET)
    public String getRadarData(Model model, @PathVariable int playerId, @PathVariable int season, @PathVariable int teamId, @PathVariable String type) {
        RadarType radarType = RadarType.fromString(type);
        Player player = playerService.getPlayerById(playerId);
        Team team = teamService.getTeamById(teamId);
        PerGameStats stats = perGameStatsService.getPerGameStatsById(player, team, season);

        RadarLayout layout = radarLayoutService.prepareRadarLayout(radarType, stats);
        String key = radarFileService.calculateKey(layout);

        String imageLink = radarFileService.getImageSrcLink(key, Map.of("playerId", playerId,
                                                                        "season", season,
                                                                        "teamId", teamId,
                                                                        "type", type));

        model.addAttribute("imageLink", imageLink);
        return "player/player-radar";
    }

    @RequestMapping(value = "/{playerId}/allSeasons", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getPlayerSeasons(Model model, @PathVariable int playerId) {
        return perGameStatsService.getOrderedSeasonsForPlayer(playerService.getPlayerById(playerId));
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
