package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.radar.category.Category;
import com.domin0x.NBARadars.radar.category.PerGameCategoryDataProvider;
import com.domin0x.NBARadars.stats.StatType;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.domin0x.NBARadars.radar.RadarTemplateConfig.statTypesByRadarTemplate;

@Service
public class RadarPrototypeFactory {
    @Autowired
    private PerGameCategoryDataProvider categoryDataProvider;

    private Map<RadarType, RadarLayout> radarTemplatesMap;

    @PostConstruct
    private void init(){
        radarTemplatesMap = initalizeTemplates();
    }

    public RadarLayout getLayoutFromTemplate(RadarType type){
        return new RadarLayout(radarTemplatesMap.get(type));
    }

    private Map<RadarType, RadarLayout> initalizeTemplates(){
        Map<RadarType, RadarLayout> radarTemplatesMap = new HashMap<>();
        for (RadarType type : RadarType.values())
            radarTemplatesMap.put(type, new RadarLayout(type.name(), getCategories(type), type));

        return radarTemplatesMap;
    }

    private List<Category<Number>> getCategories(RadarType radarType) {
        List<Category<Number>> categories = new ArrayList<>();

        for(StatType statType : statTypesByRadarTemplate.get(radarType)){
            categories.add(createCategory(statType));
        }
        return categories;
    }

    private Category<Number> createCategory(StatType statType){
        BigDecimal min = categoryDataProvider.minValue(statType);
        BigDecimal max = categoryDataProvider.maxValue(statType);
        return statType.isNegative() ? new Category<>(categoryDataProvider.getName(statType), max, min)
                : new Category<>(categoryDataProvider.getName(statType), min, max);
    }

}
