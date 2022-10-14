package com.hyunho9877.freeboard.repository;

import com.hyunho9877.freeboard.domain.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    List<FreeBoard> findByBuildingOrderByIdDesc(String building);

    @Query(value = "select f from FreeBoard f join fetch f.commentList c where f.id = :articleID")
    FreeBoard getArticleWithComments(Long articleID);
}