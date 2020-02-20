package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.player.Player;
import com.domin0x.NBARadars.radar.category.Category;
import com.domin0x.NBARadars.stats.PerGameStats;
import com.domin0x.NBARadars.stats.PerGameStatsId;
import com.domin0x.NBARadars.stats.PerGameStatsService;
import com.domin0x.NBARadars.team.Team;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RadarLayoutService.class})
public class RadarLayoutServiceTest {
    public static final StatType SAMPLE_STATTYPE = StatType.ASSISTS;
    public static final BigDecimal MAX = BigDecimal.TEN;
    public static final BigDecimal MIN = BigDecimal.ONE;
    public static final BigDecimal VALUE = BigDecimal.valueOf(5);
    public static final String SAMPLE_NAME = "Name";
    public static final int SAMPLE_SEASON = 2015;
    public static final RadarType SAMPLE_RADARTYPE = RadarType.PLAYER_BASE_STATS;
    public static final Category<Number> SAMPLE_CATEGORY = new Category<Number>(SAMPLE_STATTYPE.getDisplayName(), MIN, MAX);

    @Autowired
    RadarLayoutService service;

    @MockBean
    PerGameStatsService statsService;

    @MockBean
    private Map<RadarType, RadarLayout> radarTemplatesMap;

    @MockBean
    private Map<RadarType, List<StatType>> radarStatTypeMap;

    private RadarLayout sampleLayout;

    @Before
    public void setSampleLayout() {
        sampleLayout =  new RadarLayout(SAMPLE_RADARTYPE.getText(), List.of(SAMPLE_CATEGORY), SAMPLE_RADARTYPE);
    }

    @Test
    public void shouldCreateLayoutWithValuesSetFromTemplate(){
        PerGameStats stats = createStatsTestData(VALUE, SAMPLE_NAME, SAMPLE_SEASON);

        Mockito.when(statsService.getPerGameStatsById(any(Player.class), anyInt())).thenReturn(stats);
        Mockito.when(radarTemplatesMap.get(any(RadarType.class))).thenReturn(sampleLayout);
        Mockito.when(radarStatTypeMap.get(any(RadarType.class))).thenReturn(List.of(SAMPLE_STATTYPE));

        RadarLayout layout = service.prepareRadarLayout(RadarType.PLAYER_BASE_STATS, stats);

        Assert.assertEquals(sampleLayout.getCategories().size(), layout.getCategories().size());
        Assert.assertEquals(sampleLayout.getCategories().get(0).getInner(), layout.getCategories().get(0).getInner());
        Assert.assertEquals(sampleLayout.getCategories().get(0).getOuter(), layout.getCategories().get(0).getOuter());
        Assert.assertEquals(sampleLayout.getCategories().get(0).getName(), layout.getCategories().get(0).getName());
        Assert.assertEquals(VALUE, layout.getCategories().get(0).getValue());

    }

    @Test
    public void whenGivenValidRadarLayoutShouldReturnJsonString() throws JsonProcessingException {
        String expectedJSON = "{\"type\":\"PLAYER_BASE_STATS\",\"name\":\"base stats\",\"categories\":[{\"name\":\"Assists\",\"inner\":1,\"outer\":10,\"value\":1}]}";
        String json = service.radarToJsonString(sampleLayout);
        
        Assert.assertEquals(expectedJSON, json);
    }

    private PerGameStats createStatsTestData(BigDecimal value, String name, int year){
        Player player = new Player();
        player.setName(name);

        PerGameStatsId statsId = new PerGameStatsId();
        statsId.setPlayer(player);
        statsId.setSeason(year);
        statsId.setTeam(new Team());

        PerGameStats stats = new PerGameStats();
        stats.setAst(value);
        stats.setId(statsId);

        return stats;
    }


}
