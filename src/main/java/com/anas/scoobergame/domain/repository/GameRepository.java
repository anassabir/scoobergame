package com.anas.scoobergame.domain.repository;

import com.anas.scoobergame.domain.pojo.Game;
import com.anas.scoobergame.domain.pojo.Move;
import com.anas.scoobergame.enums.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findAllByGameStatusOrderByLastModifiedDateDesc(GameStatus gameStatus);
}
