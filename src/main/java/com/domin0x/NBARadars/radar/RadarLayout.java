package com.domin0x.NBARadars.radar;

import java.util.ArrayList;
import java.util.List;

import com.domin0x.NBARadars.radar.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RadarLayout {

    @JsonProperty("name")
    private String title;

    @JsonIgnoreProperties
    private RadarType type;

    @JsonProperty("categories")
    private List<Category<Number>> categories;

    public RadarLayout(String name, List<Category<Number>> categories, RadarType type) {
        this.title = name;
        this.categories = categories;
        this.type = type;
    }

    public RadarLayout(RadarLayout template) {
        this.title = template.title;
        this.type = template.type;
        //"deep copy" categories list - copy only name, inner, outer. Actual value field will be set for each axis later
        this.categories = new ArrayList<>();
        for (Category<Number> cat : template.categories)
            this.categories.add(new Category<>(cat.getName(), cat.getInner(), cat.getOuter()));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Category<Number>> getCategories() {
        return categories;
    }

    public void setCategories(List<Category<Number>> categories) {
        this.categories = categories;
    }

    public RadarType getType() {
        return type;
    }

    public void setType(RadarType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(title).append("categories", categories).toString();
    }

}
