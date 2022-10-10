package com.hyunho9877.doodle.repository;

import com.hyunho9877.doodle.domain.Doodle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoodleRepository extends JpaRepository<Doodle, Long> {
    List<Doodle> findByBuildingOrderByDoodleIdDesc(String building, Pageable pageable);
}