package com.anas.scoobergame;

import com.anas.scoobergame.domain.pojo.Game;
import com.anas.scoobergame.domain.pojo.Player;
import com.anas.scoobergame.dto.GameRequestObjectDTO;
import com.anas.scoobergame.dto.MoveRequestObjectDTO;
import com.anas.scoobergame.enums.GameType;
import com.anas.scoobergame.enums.PlayerKind;
import com.anas.scoobergame.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GameService gameService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getGameHealth() throws Exception {
        mockMvc.perform(get("/api/v1/game/health")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(containsString("Game is running.")));
    }


    @Test
    public void createNewGame() throws Exception {
        GameRequestObjectDTO requestObjectDTO = new GameRequestObjectDTO();
        requestObjectDTO.setGameType(GameType.SINGLEPLAYER);
        requestObjectDTO.setPlayer1Name("player 1");
        requestObjectDTO.setPlayer2Name("npc 1");
        requestObjectDTO.setInitialNumber(70);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(requestObjectDTO);

        mockMvc.perform(post("/api/v1/game")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.['game_id']").value(3))
                .andExpect(jsonPath("$.['player_1']", is("player 1")))
                .andExpect(jsonPath("$.['player_2']", is("npc 1")))
                .andExpect(jsonPath("$.['current_number']").value(23))
                .andExpect(jsonPath("$.['original_number']").value(70));
    }

    @Test
    public void performMove() throws Exception {
        MoveRequestObjectDTO requestObjectDTO = new MoveRequestObjectDTO();
        requestObjectDTO.setGameId(3);
        requestObjectDTO.setPlayerId(1);
        requestObjectDTO.setMoveNumber(1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(requestObjectDTO);

        mockMvc.perform(put("/api/v1/game")
                        .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.['game_id']").value(3))
                .andExpect(jsonPath("$.['player_1']", is("player 1")))
                .andExpect(jsonPath("$.['player_2']", is("npc 1")))
                .andExpect(jsonPath("$.['current_number']").value(8))
                .andExpect(jsonPath("$.['original_number']").value(70));
    }

    @Test
    public void addNewPlayer() throws Exception {
        Player player = new Player("player 1", PlayerKind.HUMAN);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(player);

        mockMvc.perform(post("/api/v1/player")
                        .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.['name']", is("player 1")))
                .andExpect(jsonPath("$.['playerKind']", is("HUMAN")));
    }
	
    @Test
    public void GameService() {
        assertNotNull(gameService);
    }

}
