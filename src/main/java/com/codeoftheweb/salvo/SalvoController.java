package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

@RestController

@RequestMapping("/api")

public class SalvoController {

    @Autowired
    private PlayerRepository playerRep;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private GamePlayerRepository gpRepo;

    @Autowired
    private  ShipRepository shipRepo;

    /*-----------------------------------------------------------------------------*/

    @RequestMapping("/players")
    public List<Object> getPlayer() {
        return playerRep.findAll()
                .stream()
                .map(player -> playerDTO(player))
                .collect(toList());
    }

    @RequestMapping("/games")
    public List<Object> getGames() {
        return gameRepo.findAll()
                .stream()
                .map(item -> gameDTO(item))
                .collect(Collectors.toList());
    }

    @RequestMapping("/gameplayer")
    public List<Map<String, Object>> getGamePlayer() {
        return gpRepo.findAll()
                .stream()
                .map(gamePlayer -> gamePlayerDTO(gamePlayer))
                .collect(toList());
    }

    @RequestMapping("/game_view/{gamePlayer_id}")
    public Map<String, Object> getGameView(@PathVariable Long gamePlayer_id) {
        GamePlayer currentGP = gpRepo.getOne(gamePlayer_id);

        return gameViewDTO(currentGP);
    }

    /*-----------------------------------------------------------------------------*/

    private Map<String, Object> gameDTO (Game game){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("create", game.getDate());
        dto.put("gamePlayer", game.getGamePlayers ()
                .stream()
                .map(gamePlayer -> gamePlayerDTO(gamePlayer))
                .collect(toList()));
        return dto;
    }

    private Map<String, Object> gameViewDTO (GamePlayer currentGP){
        Game game = currentGP.getGame();
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("create", game.getDate());
        dto.put("gamePlayer", game.getGamePlayers ()
                .stream()
                .map(gamePlayer -> gamePlayerDTO(gamePlayer))
                .collect(toList()));
        dto.put("ships", currentGP.getShips());

        return dto;
    }

    private Map<String, Object> playerDTO (Player player){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("userName", player.getUserName());
        dto.put("email", player.getEmail());
        return dto;
    }

    private Map<String, Object> gamePlayerDTO (GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<String,Object>();
        dto.put("id",gamePlayer.getId());
        dto.put("player", playerDTO(gamePlayer.getPlayer()));
        return dto;
    }
}

