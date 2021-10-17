package com.anas.scoobergame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.anas.scoobergame.domain.pojo.Game;
import com.anas.scoobergame.domain.pojo.Move;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameResponseDTO {

//    @JsonProperty("game")
//    Game game;
    @JsonProperty("game_id")
    private Integer gameId;
    @JsonProperty("player_1")
    private String player1;
    @JsonProperty("player_2")
    private String player2;
    @JsonProperty("current_number")
    private Integer currentNumber;
    @JsonProperty("original_number")
    private Integer originalNumber;
    @JsonProperty("moveList")
    List<MoveRspSingleDTO> moveRspSingleDTOList;

}
