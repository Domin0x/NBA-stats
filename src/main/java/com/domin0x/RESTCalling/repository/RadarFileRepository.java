package com.domin0x.RESTCalling.repository;

import com.domin0x.RESTCalling.model.RadarFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RadarFileRepository extends JpaRepository<RadarFile, Integer> {

    boolean existsByPath(String path);
}
