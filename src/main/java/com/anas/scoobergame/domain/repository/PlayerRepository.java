package com.anas.scoobergame.domain.repository;

import com.anas.scoobergame.domain.pojo.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
