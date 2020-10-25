package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.player.Player;
import com.domin0x.NBARadars.radar.category.Category;
import com.domin0x.NBARadars.stats.pergame.PerGameStats;
import com.domin0x.NBARadars.stats.pergame.PerGameStatsId;
import com.domin0x.NBARadars.stats.pergame.PerGameStatsService;
import com.domin0x.NBARadars.stats.pergame.StatType;
import com.domin0x.NBARadars.team.Team;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.stream.Collectors;

import static com.domin0x.NBARadars.radar.RadarTemplateConfig.statTypesByRadarTemplate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RadarLayoutService.class})
public class RadarLayoutServiceTest {
    public static final StatType SAMPLE_STATTYPE = StatType.ASSISTS;
    public static final BigDecimal MAX = BigDecimal.TEN;
    public static final BigDecimal MIN = BigDecimal.ZERO;
    public static final BigDecimal VALUE = BigDecimal.ONE;
    public static final Category<Number> SAMPLE_CATEGORY = new Category<>(SAMPLE_STATTYPE.getDisplayName(), MIN, MAX, VALUE);
    public static final String SAMPLE_NAME = "Name";
    public static final int SAMPLE_SEASON = 2015;
    public static final RadarType SAMPLE_RADARTYPE = RadarType.PLAYER_BASE_STATS;

    @Autowired
    RadarLayoutService service;

    @MockBean
    PerGameStatsService statsService;

    @MockBean
    RadarPrototypeFactory radarPrototypeFactory;

    @Test
    public void shouldFillLayoutWithProvidedData(){
        PerGameStats stats = createStatsTestData(VALUE, SAMPLE_NAME, SAMPLE_SEASON);
        RadarLayout templateLayout = createFakeTemplateLayout();

        Mockito.when(statsService.getPerGameStatsById(any(Player.class), anyInt())).thenReturn(stats);
        Mockito.when(radarPrototypeFactory.getClonedLayoutFromPrototype(any(RadarType.class))).thenReturn(templateLayout);

        RadarLayout testLayout = service.prepareRadarLayout(RadarType.PLAYER_BASE_STATS, stats);
        BigDecimal testStatValue = getValueForCategory(testLayout, SAMPLE_STATTYPE);

        Assert.assertTrue(testLayout.getTitle().contains(SAMPLE_NAME));
        Assert.assertTrue(testLayout.getTitle().contains(String.valueOf(SAMPLE_SEASON)));
        Assert.assertEquals(VALUE, testStatValue);
    }

    @Test
    public void whenGivenValidRadarLayoutShouldReturnJsonString() throws JsonProcessingException {
        RadarLayout expectedLayout =  new RadarLayout(SAMPLE_RADARTYPE.getText(), List.of(SAMPLE_CATEGORY), SAMPLE_RADARTYPE);
        expectedLayout.addColor("color1", "#FFFFFF");
        expectedLayout.addColor("color2", "#CCCCCC");
        String expectedJSON = "{\"type\":\"PLAYER_BASE_STATS\",\"name\":\"base stats\",\"categories\":[{\"name\":\"Assists\",\"inner\":0,\"outer\":10,\"value\":1}],\"colors\":{\"color1\":\"#FFFFFF\",\"color2\":\"#CCCCCC\"}}";
        String json = service.radarToJsonString(expectedLayout);

        Assert.assertEquals(expectedJSON, json);
    }

    private PerGameStats createStatsTestData(BigDecimal value, String name, int year){
        Player player = new Player();
        player.setName(name);

        PerGameStatsId statsId = new PerGameStatsId();
        statsId.setPlayer(player);
        statsId.setSeason(year);

        Team team = new Team();
        team.setColor1Hex("#FFFFFF");
        team.setColor2Hex("#FFFFFF");
        statsId.setTeam(team);

        PerGameStats stats = new PerGameStats();
        stats.setAst(value);
        stats.setId(statsId);

        return stats;
    }

    private BigDecimal getValueForCategory(RadarLayout layout, StatType statType) {
        return (BigDecimal) layout.getCategories().stream()
                .filter(category -> category.getName().equals(statType.getDisplayName()))
                .map(Category::getValue)
                .findFirst()
                .get();
    }

    private RadarLayout createFakeTemplateLayout() {
        List<Category<Number>> fakeCategories =
                statTypesByRadarTemplate.get(SAMPLE_RADARTYPE).stream()
                        .map(statType -> new Category<Number>(statType.getDisplayName(), MIN, MAX))
                        .collect(Collectors.toList());

        return new RadarLayout(SAMPLE_RADARTYPE.getText(), fakeCategories, SAMPLE_RADARTYPE);
    }

}
