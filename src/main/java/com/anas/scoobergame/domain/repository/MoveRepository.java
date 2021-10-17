package com.anas.scoobergame.domain.repository;

import com.anas.scoobergame.domain.pojo.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, Integer> {

    List<Move> findByGameIdOrderByLastModifiedDateDesc(Integer gameId);

}
