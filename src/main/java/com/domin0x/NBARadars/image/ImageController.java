package com.domin0x.NBARadars.image;

import com.domin0x.NBARadars.team.TeamService;
import com.domin0x.NBARadars.radar.file.RadarFileService;
import com.domin0x.NBARadars.stats.PerGameStats;
import com.domin0x.NBARadars.stats.PerGameStatsService;
import com.domin0x.NBARadars.player.Player;
import com.domin0x.NBARadars.radar.*;
import com.domin0x.NBARadars.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;


@Controller
@RequestMapping(ImageController.REQUEST_MAPPING_CONTOLLER_URL)
public class ImageController {
    public static final String REQUEST_MAPPING_CONTOLLER_URL = "/image";
    public static final String REQUEST_MAPPING_RADAR_PATH_COMPONENT = "/radar";

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;
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
        String key = radarFileService.calculateKey(getRadarLayout(playerId, season, type));

        return  radarFileService.getImageSrcLink(key, Map.of("playerId", playerId,
                                                             "season", season,
                                                             "type", type.getText()));
    }

    private RadarLayout getRadarLayout(int playerId, int season, RadarType type) {
        Player player = playerService.getPlayerById(playerId);
        PerGameStats stats = perGameStatsService.getPerGameStatsById(player, season);

        return radarLayoutService.prepareRadarLayout(type, stats);
    }


    @ResponseBody
    @RequestMapping(value = "/radar", produces = MediaType.IMAGE_PNG_VALUE )
    public byte[] getRadarImage(@RequestParam int playerId,
                                @RequestParam int season,
                                @RequestParam(required = false) Integer teamId,
                                @RequestParam String type) throws IOException {
        Player player = playerService.getPlayerById(playerId);
        PerGameStats stats = (teamId == null) ? perGameStatsService.getPerGameStatsById(player, season) :
                                                perGameStatsService.getPerGameStatsById(player, season,
                                                        teamService.getTeamById(teamId));

        RadarLayout layout = radarLayoutService.prepareRadarLayout(RadarType.fromString(type), stats);
        byte [] radarBinImage = radarWebService.getRadarImageFromAPI(radarLayoutService.radarToJsonString(layout));
        radarFileService.cacheImage(layout, radarBinImage);

        return radarBinImage;
    }
}
