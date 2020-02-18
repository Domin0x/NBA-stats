package com.domin0x.NBARadars.radar.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RadarFileRepository extends JpaRepository<RadarFile, Integer> {

    boolean existsByPath(String path);
    void deleteByPath(String path);
}
