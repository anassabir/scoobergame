package com.anas.scoobergame.domain.pojo;

import com.anas.scoobergame.enums.PlayerKind;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "PLAYER")
public class Player extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "player_kind", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlayerKind playerKind;

    @Column(name = "name", length = 50)
    private String name;

    public Player() {}

    public Player(String name, PlayerKind playerKind) {
        this.name = name;
        this.playerKind = playerKind;
    }

}
