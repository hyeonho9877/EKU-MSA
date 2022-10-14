package com.hyunho9877.infoboard.repository;

import com.hyunho9877.infoboard.domain.InfoBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InfoBoardRepository extends JpaRepository<InfoBoard, Long> {
    @Query(value = "select i from InfoBoard as i where substring(i.building, :building, 1) = '1' order by i.createdAt desc")
    List<InfoBoard> findByBuilding(String building);
}
