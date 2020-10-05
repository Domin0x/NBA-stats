package com.domin0x.NBARadars.radar.file;

import com.domin0x.NBARadars.amazon.AmazonService;
import com.domin0x.NBARadars.radar.RadarLayout;
import com.domin0x.NBARadars.radar.RadarType;
import com.domin0x.NBARadars.radar.category.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RadarFileService.class})
public class RadarFileServiceTest {
    private static final String NOT_EXISTS_IN_EITHER = "NN";
    private static final String EXISTS_IN_BOTH = "YY";
    private static final String DB_ONLY = "YN";
    private static final String AMAZON_ONLY = "NY";

    @Autowired
    RadarFileService radarFileService;

    @MockBean
    private AmazonService amazonService;

    @MockBean
    private RadarFileRepository radarFileRepository;

    @Test
    public void testCheckIfKeyExists_allTrue() {
        Mockito.when(amazonService.checkIfObjectExists(EXISTS_IN_BOTH)).thenReturn(true);
        Mockito.when(radarFileRepository.existsByPath(EXISTS_IN_BOTH)).thenReturn(true);

        boolean result = radarFileService.checkIfKeyExists(EXISTS_IN_BOTH);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testCheckIfKeyExists_negative() {
        Mockito.when(amazonService.checkIfObjectExists(AMAZON_ONLY)).thenReturn(true);
        Mockito.when(amazonService.checkIfObjectExists(NOT_EXISTS_IN_EITHER)).thenReturn(false);
        Mockito.when(amazonService.checkIfObjectExists(DB_ONLY)).thenReturn(false);

        Mockito.when(radarFileRepository.existsByPath(DB_ONLY)).thenReturn(true);
        Mockito.when(radarFileRepository.existsByPath(NOT_EXISTS_IN_EITHER)).thenReturn(false);
        Mockito.when(radarFileRepository.existsByPath(AMAZON_ONLY)).thenReturn(false);

        boolean amazonOnly = radarFileService.checkIfKeyExists(AMAZON_ONLY);
        boolean dbOnly = radarFileService.checkIfKeyExists(DB_ONLY);
        boolean neither = radarFileService.checkIfKeyExists(NOT_EXISTS_IN_EITHER);

        Assert.assertEquals(false, amazonOnly);
        Assert.assertEquals(false, dbOnly);
        Assert.assertEquals(false, neither);

    }

    @Test
    public void testGetImageSrcLink_usesAmazon(){
        Map<String, Object> params = createPropertiesMap();


        ReflectionTestUtils.setField(radarFileService, "useAmazonCache", true);
        Mockito.when(radarFileRepository.existsByPath(EXISTS_IN_BOTH)).thenReturn(true);
        Mockito.when(amazonService.checkIfObjectExists(EXISTS_IN_BOTH)).thenReturn(true);
        Mockito.when(amazonService.getObjectURL(EXISTS_IN_BOTH)).thenReturn("AMAZON_LINK");

        radarFileService.getImageSrcLink(EXISTS_IN_BOTH, params);
        Mockito.verify(amazonService).getObjectURL(EXISTS_IN_BOTH);
    }

    @Test
    public void testGetImageSrcLink_notUsesAmazonBecauseOfKey(){
        Map<String, Object> params = createPropertiesMap();

        ReflectionTestUtils.setField(radarFileService, "useAmazonCache", true);
        Mockito.when(radarFileRepository.existsByPath(NOT_EXISTS_IN_EITHER)).thenReturn(false);
        Mockito.when(amazonService.checkIfObjectExists(NOT_EXISTS_IN_EITHER)).thenReturn(false);

        radarFileService.getImageSrcLink(NOT_EXISTS_IN_EITHER, params);
        Mockito.verify(amazonService, Mockito.never()).getObjectURL(NOT_EXISTS_IN_EITHER);
    }

    @Test
    public void testGetImageSrcLink_notUsesAmazonBecauseOfProperty(){
        Map<String, Object> params = createPropertiesMap();

        ReflectionTestUtils.setField(radarFileService, "useAmazonCache", false);
        Mockito.when(radarFileRepository.existsByPath(EXISTS_IN_BOTH)).thenReturn(true);
        Mockito.when(amazonService.checkIfObjectExists(EXISTS_IN_BOTH)).thenReturn(true);

        radarFileService.getImageSrcLink(EXISTS_IN_BOTH, params);
        Mockito.verify(amazonService, Mockito.never()).getObjectURL(EXISTS_IN_BOTH);
    }

    @Test
    public void testCalculateKey(){
        String exampleTitle = "James Jones 2016 base_stats";
        List<Category<Number>> categories = List.of(new Category<Number>("pts", 0, 30.221, 20), new Category<Number>("ast", 0, 10.1, 1));
        RadarLayout radarLayout = new RadarLayout(exampleTitle, categories, RadarType.PLAYER_BASE_STATS);
        String expected = "JamesJones2016base_stats" + "pts030.2220ast010.11" + RadarFileService.SUFFIX;

        String result = radarFileService.generateKey(radarLayout);
        Assert.assertEquals(expected , result);
    }

    private Map<String, Object> createPropertiesMap() {
        final int playerId = 0;
        final int season = 0;
        final RadarType type = RadarType.PLAYER_BASE_STATS;

        return Map.of("playerId", playerId,
                "season", season,
                "type", type.getText());
    }
}
