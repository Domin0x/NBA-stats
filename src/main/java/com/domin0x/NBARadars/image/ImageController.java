package com.domin0x.NBARadars.image;

import com.domin0x.NBARadars.NBARadarsApplication;
import com.domin0x.NBARadars.radar.RadarForm;
import com.domin0x.NBARadars.radar.file.RadarFileService;
import com.domin0x.NBARadars.stats.PerGameStats;
import com.domin0x.NBARadars.stats.PerGameStatsService;
import com.domin0x.NBARadars.player.Player;
import com.domin0x.NBARadars.radar.*;
import com.domin0x.NBARadars.team.Team;
import com.domin0x.NBARadars.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;


@Controller
@RequestMapping("/image")
public class ImageController {

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
    private RadarFileService radarFileService;

    @ResponseBody
    @RequestMapping(value="/radar_link", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getRadarImageLink(@RequestBody RadarForm radarForm){
        int playerId = radarForm.getPlayerId();
        int season = radarForm.getYear();
        RadarType type = radarForm.getRadarType();

        Player player = playerService.getPlayerById(playerId);
        PerGameStats stats = perGameStatsService.getPerGameStatsById(player, season);

        RadarLayout layout = radarLayoutService.prepareRadarLayout(type, stats);
        String key = radarFileService.calculateKey(layout);

        return  radarFileService.getImageSrcLink(key, Map.of("playerId", playerId,
                                                             "season", season,
                                                             "type", type.getText()));
    }


    @ResponseBody
    @RequestMapping(value = "/radar", produces = MediaType.IMAGE_PNG_VALUE )
    public byte[] getRadarImage(@RequestParam int playerId,
                                @RequestParam int season,
                                @RequestParam(required = false) Integer teamId,
                                @RequestParam String type) throws IOException {
        RadarType radarType = RadarType.fromString(type);
        Player player = playerService.getPlayerById(playerId);
        PerGameStats stats;
        if (teamId != null) {
            Team team = teamService.getTeamById(teamId);
            stats = perGameStatsService.getPerGameStatsById(player, team, season);
        }
        else {
            stats = perGameStatsService.getPerGameStatsById(player, season);
        }

        RadarLayout layout = radarLayoutService.prepareRadarLayout(radarType, stats);
        byte [] radarBinImage = radarWebService.getRadarImageFromAPI(radarLayoutService.radarToJsonString(layout));

        radarFileService.cacheImage(layout, radarBinImage);

        return radarBinImage;
    }
}
