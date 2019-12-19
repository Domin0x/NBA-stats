package com.domin0x.RESTCalling.jsonwrappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerWrapper {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("height_feet")
    private Integer heightFeet;
    @JsonProperty("height_inches")
    private Integer heightInches;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("position")
    private String position;
    @JsonProperty("weight_pounds")
    private Integer weightPounds;


    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("height_feet")
    public Integer getHeightFeet() {
        return heightFeet;
    }

    @JsonProperty("height_feet")
    public void setHeightFeet(Integer heightFeet) {
        this.heightFeet = heightFeet;
    }

    @JsonProperty("height_inches")
    public Integer getHeightInches() {
        return heightInches;
    }

    @JsonProperty("height_inches")
    public void setHeightInches(Integer heightInches) {
        this.heightInches = heightInches;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("position")
    public String getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(String position) {
        this.position = position;
    }

    @JsonProperty("weight_pounds")
    public Integer getWeightPounds() {
        return weightPounds;
    }

    @JsonProperty("weight_pounds")
    public void setWeightPounds(Integer weightPounds) {
        this.weightPounds = weightPounds;
    }

    @Override
    public String toString() {
        return "PlayerWrapper{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", heightFeet=" + heightFeet +
                ", heightInches=" + heightInches +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", weightPounds=" + weightPounds +
                '}';
    }
}