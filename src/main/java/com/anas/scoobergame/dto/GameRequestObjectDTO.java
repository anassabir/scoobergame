package com.anas.scoobergame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.anas.scoobergame.enums.GameType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRequestObjectDTO {

    @JsonProperty(value = "game_type")
    private GameType gameType;

    @JsonProperty(value = "player_1_name")
    private String player1Name;

    @JsonProperty(value = "player_2_name")
    private String player2Name;

    @JsonProperty(value = "initial_number")
    private Integer initialNumber;

}
