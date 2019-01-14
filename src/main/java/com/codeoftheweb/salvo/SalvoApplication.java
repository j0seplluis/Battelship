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

            Player p4 = new Player("David", "Palmer", "David", "d.palmer@whitehouse.gov");
            repository.save(p4);

            Player p5 = new Player("Michelle", "Dessler", "Michelle", "m.dessler@ctu.gov");
            repository.save(p5);

            /*------------------------------------------------------------------------------------------------------------*/

            Game g1 = new Game();
            gameRepo.save(g1);

            Game g2 = new Game();
            gameRepo.save(g2);

            Game g3 = new Game();
            gameRepo.save(g3);

            Game g4 = new Game();
            gameRepo.save(g4);

            /*------------------------------------------------------------------------------------------------------------*/

            GamePlayer gp1 = new GamePlayer(p1, g1);
            gpRepo.save(gp1);

            GamePlayer gp2 = new GamePlayer(p2, g1);
            gpRepo.save(gp2);

            GamePlayer gp3 = new GamePlayer(p1, g2);
            gpRepo.save(gp3);

            GamePlayer gp4 = new GamePlayer(p2, g2);
            gpRepo.save(gp4);

            GamePlayer gp5 = new GamePlayer(p3, g3);
            gpRepo.save(gp5);

            GamePlayer gp6 = new GamePlayer(p4, g3);
            gpRepo.save(gp6);

            GamePlayer gp7 = new GamePlayer(p4, g4);
            gpRepo.save(gp7);

            /*------------------------------------------------------------------------------------------------------------*/

            Ship ship1 = new Ship("Patrol Boat", gp1, Arrays.asList("A4", "A5"));
            shipRepo.save(ship1);

            Ship ship2 = new Ship("Destroyer", gp1, Arrays.asList("H2","H3","H4"));
            shipRepo.save(ship2);

            Ship ship3 = new Ship("Submarine", gp1, Arrays.asList("E1", "F1", "G1"));
            shipRepo.save(ship3);

            Ship ship4 = new Ship("Carrier", gp1, Arrays.asList("H6", "H7", "H8", "H9", "H10"));
            shipRepo.save(ship4);

            Ship ship5 = new Ship("Battleship", gp1, Arrays.asList("E2","E3", "E4", "E5"));
            shipRepo.save(ship5);

            /*------------------------------------------------------------------------------------------------------------*/

            Ship ship6 = new Ship("Patrol Boat", gp2, Arrays.asList("A3", "B3", "C3"));
            shipRepo.save(ship6);

            Ship ship7 = new Ship("Destroyer", gp2, Arrays.asList("H2","H3","H4"));
            shipRepo.save(ship7);

            Ship ship8 = new Ship("Submarine", gp2, Arrays.asList("E1", "F1", "G1"));
            shipRepo.save(ship8);

            Ship ship9 = new Ship("Carrier", gp2, Arrays.asList("H6", "H7", "H8", "H9", "H10"));
            shipRepo.save(ship9);

            Ship ship10 = new Ship("Battleship", gp2, Arrays.asList("E2","E3", "E4", "E5"));
            shipRepo.save(ship10);

            /*------------------------------------------------------------------------------------------------------------*/


        };
    }
}

