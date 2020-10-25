package com.domin0x.NBARadars.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "teams")
public class Team {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @JsonProperty("full_name")
    @Column(name="full_name")
    private String fullName;
    @Column(name="name")
    private String name;
    @Column(name="abbreviation")
    private String abbreviation;
    @Column(name="conference")
    private String conference;
    @Column(name="city")
    private String city;
    @Column(name="division")
    private String division;
    @Column(name="color1_hex")
    private String color1Hex;
    @Column(name="color2_hex")
    private String color2Hex;

    public Integer getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getColor1Hex() {
        return color1Hex;
    }

    public String getColor2Hex() {
        return color2Hex;
    }

    public void setColor1Hex(String color1Hex) {
        this.color1Hex = color1Hex;
    }

    public void setColor2Hex(String color2Hex) {
        this.color2Hex = color2Hex;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", conference='" + conference + '\'' +
                ", city='" + city + '\'' +
                ", division='" + division + '\'' +
                '}';
    }
}
