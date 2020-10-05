package com.domin0x.NBARadars.radar.file;

import com.domin0x.NBARadars.amazon.AmazonService;
import com.domin0x.NBARadars.image.ImageController;
import com.domin0x.NBARadars.radar.RadarLayout;
import com.domin0x.NBARadars.radar.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class RadarFileService {
    public static final String SUFFIX = ".png";

    @Autowired
    private AmazonService amazonService;

    @Autowired
    private RadarFileRepository radarFileRepository;

    @Value("${radarImage.useAmazonS3}")
    boolean useAmazonCache;

    public boolean checkIfKeyExists(String key){
        return radarFileRepository.existsByPath(key) && amazonService.checkIfObjectExists(key);
    }

    public String generateKey(RadarLayout radarLayout){
        return withoutWhitespaces(radarLayout.getTitle()) + categoriesToKeyString(radarLayout.getCategories()) + SUFFIX;
    }

    private String withoutWhitespaces(String str) {
        return str.replaceAll("\\s", "");
    }

    private String categoriesToKeyString(List<Category<Number>> categories) {
        return categories.stream()
                .map(this::mapCategoryToKeyPart)
                .collect(Collectors.joining(""));
    }

    private String mapCategoryToKeyPart(Category<Number> category){
        return category.getName() +
               getStringRepresentation(category.getInner()) +
               getStringRepresentation(category.getOuter()) +
               getStringRepresentation(category.getValue());
    }

    private String getStringRepresentation(Number number) {
        if (number == null)
            return "-";
        NumberFormat numFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        numFormat.setMaximumFractionDigits(2);
        return numFormat.format(number);
    }

    @Async("processExecutor")
    public void cacheImage(RadarLayout radarLayout, byte [] content) {
            String key = generateKey(radarLayout);
            amazonService.uploadFile(key, content);
            if (!radarFileRepository.existsByPath(key))
                radarFileRepository.save(new RadarFile(key));
    }

    public String getImageSrcLink(String key, Map<String, Object> paramMap){
        if (useAmazonCache && checkIfKeyExists(key))
            return amazonService.getObjectURL(key);

        return getURLWithParams(paramMap);
    }

    private String getURLWithParams(Map<String, Object> paramMap) {
        MultiValueMap<String, String> params = convertToMultiValueMap(paramMap);
        final String path = ImageController.REQUEST_MAPPING_BASE + ImageController.REQUEST_MAPPING_RADAR;

        return UriComponentsBuilder.fromPath(path).queryParams(params).build().encode().toUriString();
    }

    private MultiValueMap<String, String> convertToMultiValueMap(Map<String, Object> paramMap) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            params.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return params;
    }

    public void deleteAllCachedImages(){
        amazonService.deleteAllInBucket();
        radarFileRepository.deleteAll();
    }

}
