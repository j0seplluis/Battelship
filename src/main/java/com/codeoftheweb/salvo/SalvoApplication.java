package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;


@SpringBootApplication
public class SalvoApplication {


    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(PlayerRepository repository, GameRepository gameRepo, GamePlayerRepository gpRepo, ShipRepository shipRepo) {
        return (args) -> {
            // save a couple of customers

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

            ArrayList<String> Destoyer = new ArrayList<>();
            Destoyer.add("H1");
            Destoyer.add("H2");
            Destoyer.add("H3");

            Ship ship1 = new Ship("Destoyer", gp1, Destoyer);
            shipRepo.save(ship1);

            Ship ship2 = new Ship();
            shipRepo.save(ship2);

            Ship ship3 = new Ship();
            shipRepo.save(ship3);


        };
    }
}

