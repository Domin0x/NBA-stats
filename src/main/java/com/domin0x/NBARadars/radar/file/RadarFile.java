package com.domin0x.NBARadars.radar.file;

import javax.persistence.*;

@Entity
@Table(name = "image_files")
public class RadarFile {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="path", unique = true)
    private String path;

    public RadarFile(){}

    public RadarFile(String path) {
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "RadarFile{" +
                "id=" + id +
                ", path='" + path + '\'' +
                '}';
    }
}

