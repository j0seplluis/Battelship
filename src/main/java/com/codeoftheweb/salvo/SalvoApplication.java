package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class SalvoApplication {


    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(PlayerRepository repository, GameRepository gameRepo, GamePlayerRepository gpRepo, ShipRepository shipRepo) {
        return (args) -> {

            Player p1 = new Player("Jack", "Bauer", "Jack", "j.bauer@ctu.gov");
            repository.save(p1);

            Player p2 = new Player("Chloe", "O'Brian", "Chloe", "c.obrian@ctu.gov");
            repository.save(p2);

            Player p3 = new Player("Kim", "Bauer", "Kim", "kim_bauer@gmail.com");
            repository.save(p3);

            Player p4 = new Player("Tony", "Almeida", "Tony", "t.almeida@ctu.gov");
            repository.save(p4);

            /*------------------------------------------------------------------------------------------------------------*/

            Game g1 = new Game();
            gameRepo.save(g1);

            Game g2 = new Game();
            gameRepo.save(g2);

            Game g3 = new Game();
            gameRepo.save(g3);

            Game g4 = new Game();
            gameRepo.save(g4);

            Game g5 = new Game();
            gameRepo.save(g5);

            Game g6 = new Game();
            gameRepo.save(g6);

            Game g7 = new Game();
            gameRepo.save(g7);

            Game g8 = new Game();
            gameRepo.save(g8);
            /*------------------------------------------------------------------------------------------------------------*/

            GamePlayer gp1 = new GamePlayer(p1, g1);
            gpRepo.save(gp1);

            GamePlayer gp2 = new GamePlayer(p2, g1);
            gpRepo.save(gp2);

            GamePlayer gp3 = new GamePlayer(p1, g2);
            gpRepo.save(gp3);

            GamePlayer gp4 = new GamePlayer(p2, g2);
            gpRepo.save(gp4);

            GamePlayer gp5 = new GamePlayer(p2, g3);
            gpRepo.save(gp5);

            GamePlayer gp6 = new GamePlayer(p4, g3);
            gpRepo.save(gp6);

            GamePlayer gp7 = new GamePlayer(p2, g4);
            gpRepo.save(gp7);

            GamePlayer gp8 = new GamePlayer(p4, g4);
            gpRepo.save(gp8);

            GamePlayer gp9 = new GamePlayer(p4, g5);
            gpRepo.save(gp9);

            GamePlayer gp11 = new GamePlayer(p3, g6);
            gpRepo.save(gp11);

            GamePlayer gp12 = new GamePlayer(p4, g7);
            gpRepo.save(gp12);

            GamePlayer gp13 = new GamePlayer(p3, g8);
            gpRepo.save(gp13);

            GamePlayer gp14 = new GamePlayer(p4, g8);
            gpRepo.save(gp14);

            /*------------------------------------------------------------------------------------------------------------*/

            Ship ship1 = new Ship("Patrol_Boat", gp1, Arrays.asList("B4", "B5"));
            shipRepo.save(ship1);

            Ship ship2 = new Ship("Destroyer", gp1, Arrays.asList("H2","H3","H4"));
            shipRepo.save(ship2);

            Ship ship3 = new Ship("Submarine", gp1, Arrays.asList("E1", "F1", "G1"));
            shipRepo.save(ship3);

            /*------------------------------------------------------------------------------------------------------------*/

            Ship ship4 = new Ship("Patrol_Boat", gp2, Arrays.asList("F1", "F2"));
            shipRepo.save(ship4);

            Ship ship5 = new Ship("Destroyer", gp2, Arrays.asList("B5","C5","D5"));
            shipRepo.save(ship5);

            /*------------------------------------------------------------------------------------------------------------*/
/*

            Ship ship11 = new Ship("Patrol_Boat", gp3, Arrays.asList("F1", "F2"));
            shipRepo.save(ship11);

            Ship ship12 = new Ship("Destroyer", gp3, Arrays.asList("B5","C5","D5"));
            shipRepo.save(ship12);

            Ship ship13 = new Ship("Submarine", gp3, Arrays.asList("E1", "F1", "G1"));
            shipRepo.save(ship13);

            Ship ship14 = new Ship("Carrier", gp3, Arrays.asList("H6", "H7", "H8", "H9", "H10"));
            shipRepo.save(ship14);

            Ship ship15 = new Ship("Battleship", gp3, Arrays.asList("E2","E3", "E4", "E5"));
            shipRepo.save(ship15);
*/

            /*------------------------------------------------------------------------------------------------------------*/


        };
    }
}

