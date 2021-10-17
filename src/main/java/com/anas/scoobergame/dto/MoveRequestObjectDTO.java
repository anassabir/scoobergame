package com.anas.scoobergame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveRequestObjectDTO {

    @JsonProperty(value = "game_id")
    private Integer gameId;

    @JsonProperty(value = "player_id")
    private Integer playerId;

    @JsonProperty(value = "move_number")
    private Integer moveNumber;

}
