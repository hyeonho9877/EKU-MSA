package com.hyunho9877.doodle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class DoodleDTO {
    private Long doodleId;
    private String content="";
    private String building;

    public DoodleDTO(String content, String building) {
        this.content = content;
        this.building = building;
    }

    public DoodleDTO(Long doodleId) {
        this.doodleId = doodleId;
    }

    public DoodleDTO(Long doodleId, String content) {
        this.doodleId = doodleId;
        this.content = content;
    }
}
