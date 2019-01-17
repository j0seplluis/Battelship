package com.codeoftheweb.salvo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Ship {


    //attributes

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String shipType;

    @ElementCollection
    @Column(name = "shipLocation")
    private List<String> shipLocation = new ArrayList<>();

   // @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gameplayer")
    private GamePlayer gamePlayer;


    //constructor

    public Ship() {

    }

    public Ship(String type, GamePlayer gamePlayer, List<String> shipLocation) {
        this.shipType = type;
        this.gamePlayer = gamePlayer;
        this.shipLocation = shipLocation;
    }


    //methods

    public Long getId() {
        return id;
    }

    public String getType() {
        return shipType;
    }

    public void setType(String type) {
        this.shipType = type;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getShipLocation() {
        return shipLocation;
    }

    public void setShipLocation(List<String> shipLocation) {
        this.shipLocation = shipLocation;
    }
}
