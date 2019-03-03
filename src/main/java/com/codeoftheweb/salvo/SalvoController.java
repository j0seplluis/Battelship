package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    //APIs

    @RequestMapping("/games/players/{gamePlayerId}/salvos")
    public ResponseEntity <Map<String, Object>>salvos(@PathVariable Long gamePlayerId, Authentication authentication, @RequestBody Salvo salvos){
        Player loggedPlayer = playerRep.findByEmail(authentication.getName());
        GamePlayer currentGP = gpRepo.getOne(gamePlayerId);

        if(loggedPlayer == null){
            return new ResponseEntity<>(makeMap("error", "please log in"), HttpStatus.UNAUTHORIZED);
        }
        if(currentGP == null){
            return new ResponseEntity<>(makeMap("error", "please join a game"), HttpStatus.UNAUTHORIZED);
        }
        if(!loggedPlayer.getId().equals(currentGP.getPlayer().getId())){
            return new ResponseEntity<>(makeMap("error", "This is not your game"), HttpStatus.UNAUTHORIZED);
        }
        if(currentGP.getSalvo().size() != 0){
            return new ResponseEntity<>(makeMap("error", "You already placed your salvos"), HttpStatus.FORBIDDEN);
        } else {
            salvos.setTurn(currentGP.getSalvo().size() +1);
            currentGP.addSalvo(salvos);
            salvoRep.save(salvos);
            }
        return new ResponseEntity<>(makeMap("success", "you fired your salvos"), HttpStatus.CREATED);
    }

    @RequestMapping("/games/players/{gamePlayerId}/ships")
    public ResponseEntity<Map<String, Object>>placeShips(@PathVariable Long gamePlayerId, Authentication authentication, @RequestBody Set<Ship> ships){
        Player loggedPlayer = playerRep.findByEmail(authentication.getName());
        GamePlayer currentGP = gpRepo.getOne(gamePlayerId);

        if(loggedPlayer == null){
            return new ResponseEntity<>(makeMap("error", "please log in"), HttpStatus.UNAUTHORIZED);
        }
        if(currentGP == null){
            return new ResponseEntity<>(makeMap("error", "please join a game"), HttpStatus.UNAUTHORIZED);
        }
        if(!loggedPlayer.getId().equals(currentGP.getPlayer().getId())){
            return new ResponseEntity<>(makeMap("error", "This is not your game"), HttpStatus.UNAUTHORIZED);
        }
        if(currentGP.getShips().size() != 0){
            return new ResponseEntity<>(makeMap("error", "You already placed your ships"), HttpStatus.FORBIDDEN);
        }
        if(ships.size() != 5){
            return new ResponseEntity<>(makeMap("error", "There should be only 5 ships"), HttpStatus.FORBIDDEN);
        }else {
            ships.forEach(s -> {
                currentGP.addShip(s);
                shipRepo.save(s);
            });
            return new ResponseEntity<>(makeMap("success","you got ships"), HttpStatus.CREATED);
        }
    }

    @RequestMapping(path = "/game/{game_id}/players", method = RequestMethod.POST)
    public ResponseEntity <Map<String,Object>> joinGame(@PathVariable Long game_id, Authentication authentication){

    Game game = gameRepo.getOne(game_id);
    Player loggedPlayer = playerRep.findByEmail(authentication.getName());

        if(loggedPlayer == null){
            return new ResponseEntity<>(makeMap("error", "please log in"), HttpStatus.UNAUTHORIZED);
        }
        if(game == null){
            return new ResponseEntity<>(makeMap("error", "There is no game to play"), HttpStatus.FORBIDDEN);
        }
        if(game.getGamePlayers().size() == 2){
            return new ResponseEntity<>(makeMap("error", "Game is full"), HttpStatus.FORBIDDEN);
        }else{
            GamePlayer gamePlayer = new GamePlayer(loggedPlayer, game);
            gpRepo.save(gamePlayer);
            return getCreatedGp(gamePlayer);
        }
    }

    @RequestMapping("/game_view/{gamePlayer_id}")
    public ResponseEntity <Map<String, Object>> getGameView(@PathVariable Long gamePlayer_id, Authentication authentication) {

        GamePlayer currentGP = gpRepo.getOne(gamePlayer_id);
        Player owner = currentGP.getPlayer();
        Player loggedPlayer = playerRep.findByEmail(authentication.getName());

        if(owner.equals(loggedPlayer)) {
            return new ResponseEntity<>(makeMap("owner", gameViewDTO(currentGP)), HttpStatus.OK);
        }
        if(!owner.equals(loggedPlayer)){
            return new ResponseEntity<>(makeMap("error", "Good try"), HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(makeMap("error", "Good try"), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping("/players")
    public List<Object> getPlayer() {
        return playerRep.findAll()
                .stream()
                .map(player -> playerDTO(player))
                .collect(toList());
    }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(String name, String pwd) {

        if (name.isEmpty() || pwd.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "Missing data"), HttpStatus.BAD_REQUEST);
        }
        if (playerRep.findByEmail(name) != null) {
            return new ResponseEntity<>(makeMap("error", "Name already in use"), HttpStatus.CONFLICT);
        }else {
            playerRep.save(new Player(name, passwordEncoder.encode(pwd)));
            return new ResponseEntity<>(makeMap("email", name), HttpStatus.CREATED);
        }
    }

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(Authentication authentication){

        Player loggedPlayer = playerRep.findByEmail(authentication.getName());

        if(loggedPlayer == null){
            return new ResponseEntity<>(makeMap("Error","Log in, please"),HttpStatus.UNAUTHORIZED);
        }else{

            Game game = new Game();
            gameRepo.save(game);

            GamePlayer gamePlayer = new GamePlayer(loggedPlayer, game);
            gpRepo.save(gamePlayer);

            return getCreatedGp(gamePlayer);
        }
    }

    @RequestMapping("/games")
    public Map<String, Object> getGames(Authentication authentication) {
        Map<String, Object> dto = new HashMap<>();

        if (authentication == null){
            dto.put("player", null);
        }
        if (authentication != null) {
            Player loggedPlayer = playerRep.findByEmail(authentication.getName());
            dto.put("player", playerDTO(loggedPlayer));
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

    @RequestMapping("/leaderboard")
    public Map<String, Object> getScores() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        List<GamePlayer> gamePlayers = gpRepo.findAll();
        for (GamePlayer gp : gamePlayers) {
            Map<String, Object> scores = new LinkedHashMap<>();
            if (!scores.containsKey(gp.getPlayer().getUserName())) {
                if(gp.getScore() != null) {
                    scores.put("Wins", gp.getPlayer().getScore().stream().filter(score -> score.getScore() == 1).count());
                    scores.put("Tie", gp.getPlayer().getScore().stream().filter(score -> score.getScore() == 0.5).count());
                    scores.put("Lost", gp.getPlayer().getScore().stream().filter(score -> score.getScore() == 0).count());
                    scores.put("Total", gp.getPlayer().getScore().stream().mapToDouble(score -> score.getScore()).sum());
                    dto.put(gp.getPlayer().getUserName(), scores);
                }
            }
        }
        return dto;
    }

    /*-----------------------------------------------------------------------------*/
    //DTOs

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
        if(getOpponent(currentGP) != null) {
            dto.put("Opponent", getOpponent(currentGP).getSalvo().stream().map(salvo -> salvoDTO(salvo)).collect(toList()));
        }else{
            dto.put("Opponent", null);
        }
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

    private Map<String,Object> makeMap(String key, Object value){
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private ResponseEntity<Map<String, Object>> getCreatedGp(GamePlayer gamePlayer) {
        return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private Player isAuth (Authentication authentication){
        return playerRep.findByEmail(authentication.getName());
    }

    private GamePlayer getOpponent(GamePlayer gamePlayer) {
        return gamePlayer.getGame()
                .getGamePlayers()
                .stream()
                .filter(gamePlayer1 -> gamePlayer1.getId() != gamePlayer.getId())
                .findAny()
                .orElse(null);
    }
}

