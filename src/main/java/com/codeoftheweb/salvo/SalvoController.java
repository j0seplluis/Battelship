package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    private ShipRepository shipRepo;

    @Autowired
    private SalvoRepository salvoRep;

    @Autowired
    private ScoreRepository scoreRep;

    @Autowired
    PasswordEncoder passwordEncoder;

    /*-----------------------------------------------------------------------------*/

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String first, @RequestParam String last,
            @RequestParam String email, @RequestParam String password) {

        if (first.isEmpty() || last.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRep.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRep.save(new Player(first, last, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/players")
    public List<Object> getPlayer() {
        return playerRep.findAll()
                .stream()
                .map(player -> playerDTO(player))
                .collect(toList());
    }

    @RequestMapping("/games")
    public Map<String, Object> getGames(Authentication authentication) {
        Map<String, Object> dto = new HashMap<>();

        if (authentication == null){
            dto.put("player", null);
        }
        if (authentication != null) {
            Player loggedPlayer = playerRep.findByEmail(authentication.getName());
            dto.put("player", loggedPlayer.getEmail());
        }
        dto.put("games", gameRepo.findAll()
                .stream()
                .map(item -> gameDTO(item))
                .collect(Collectors.toList()));
        return dto;
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

    @RequestMapping("/leaderboard")
    public Map<String, Object> getScores() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        List<GamePlayer> gamePlayers = gpRepo.findAll();
        for (GamePlayer gp : gamePlayers) {
            Map<String, Object> scores = new LinkedHashMap<>();
            if (!scores.containsKey(gp.getPlayer().getUserName())) {
                scores.put("Wins", gp.getPlayer().getScore().stream().filter(score -> score.getScore() == 1).count());
                scores.put("Tie", gp.getPlayer().getScore().stream().filter(score -> score.getScore() == 0.5).count());
                scores.put("Lost", gp.getPlayer().getScore().stream().filter(score -> score.getScore() == 0).count());
                scores.put("Total", gp.getPlayer().getScore().stream().mapToDouble(score -> score.getScore()).sum());
                dto.put(gp.getPlayer().getUserName(), scores);
            }
        }
        return dto;
    }

    /*-----------------------------------------------------------------------------*/

    private Map<String, Object> gameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("create", game.getDate());
        dto.put("gamePlayer", game.getGamePlayers()
                .stream()
                .map(gamePlayer -> gamePlayerDTO(gamePlayer))
                .collect(toList()));
        return dto;
    }

    private Map<String, Object> gameViewDTO(GamePlayer currentGP) {
        Game game = currentGP.getGame();
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("create", game.getDate());
        dto.put("gamePlayer", game.getGamePlayers()
                .stream()
                .map(gamePlayer -> gamePlayerDTO(gamePlayer))
                .collect(toList()));
        dto.put("ships", currentGP.getShips().stream().map(ship -> shipDTO(ship)).collect(toList()));
        dto.put("salvo", currentGP.getSalvo().stream().map(salvo -> salvoDTO(salvo)).collect(toList()));
        dto.put("Opponent", getOpponent(currentGP).getSalvo().stream().map(salvo -> salvoDTO(salvo)).collect(toList()));
        return dto;
    }

    private Map<String, Object> playerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("userName", player.getUserName());
        dto.put("email", player.getEmail());
        return dto;
    }

    private Map<String, Object> gamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", playerDTO(gamePlayer.getPlayer()));
        if (gamePlayer.getScore() != null) {
            dto.put("score", gamePlayer.getScore().getScore());
        } else {
            dto.put("score", null);
        }
        return dto;
    }

    private Map<String, Object> shipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", ship.getType());
        dto.put("shipLocation", ship.getShipLocation());
        return dto;
    }

    private Map<String, Object> salvoDTO(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", salvo.getTurn());
        dto.put("salvoLocation", salvo.getSalvoLocation());
        return dto;
    }

    private Map<String, Object> scoreDTO(Score score) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("score", score.getScore());
        dto.put("endDate", score.getEndDate());
        dto.put("player", score.getPlayer());
        return dto;
    }



    /*-----------------------------------------------------------------------------*/
    //common methods

    private GamePlayer getOpponent(GamePlayer gamePlayer) {
        return gamePlayer.getGame()
                .getGamePlayers()
                .stream()
                .filter(gamePlayer1 -> gamePlayer1.getId() != gamePlayer.getId())
                .findAny()
                .orElse(null);

    }

}

