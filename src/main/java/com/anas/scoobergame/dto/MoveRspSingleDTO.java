package com.anas.scoobergame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveRspSingleDTO {

    @JsonProperty("move_description")
    private String moveDescription;

    public MoveRspSingleDTO() {}
    public MoveRspSingleDTO(String moveDescription) {
        this.moveDescription = moveDescription;
    }

}
