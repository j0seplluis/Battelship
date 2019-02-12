package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@SpringBootApplication
public class SalvoApplication {


    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initData( PlayerRepository repository, GameRepository gameRepo, GamePlayerRepository gpRepo, ShipRepository shipRepo, SalvoRepository salvoRep, ScoreRepository scoreRep) {
        return (args) -> {

            Player p1 = new Player("Jack", "Bauer", "jBauer", "j.bauer@ctu.gov", passwordEncoder().encode("24"));
            repository.save(p1);

            Player p2 = new Player("Chloe", "O'Brian", "cOBrian","c.obrian@ctu.gov", passwordEncoder().encode("42"));
            repository.save(p2);

            Player p3 = new Player("Kim", "Bauer", "kBauer","kim_bauer@gmail.com", passwordEncoder().encode("kb"));
            repository.save(p3);

            Player p4 = new Player("Tony", "Almeida", "tAlmeida","t.almeida@ctu.gov", passwordEncoder().encode("mole"));
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

            GamePlayer gp10 = new GamePlayer(p1, g5);
            gpRepo.save(gp10);

            GamePlayer gp11 = new GamePlayer(p3, g6);
            gpRepo.save(gp11);

            GamePlayer gp12 = new GamePlayer(p4, g7);
            gpRepo.save(gp12);

            GamePlayer gp13 = new GamePlayer(p3, g8);
            gpRepo.save(gp13);

            GamePlayer gp14 = new GamePlayer(p4, g8);
            gpRepo.save(gp14);

            /*------------------------------------------------------------------------------------------------------------*/

            //Owner Game 1
            Ship ship1 = new Ship("Patrol_Boat", gp1, Arrays.asList("B4", "B5"));
            shipRepo.save(ship1);

            Ship ship2 = new Ship("Destroyer", gp1, Arrays.asList("H2", "H3", "H4"));
            shipRepo.save(ship2);

            Ship ship3 = new Ship("Submarine", gp1, Arrays.asList("E1", "F1", "G1"));
            shipRepo.save(ship3);

            /*------------------------------------------------------------------------------------------------------------*/

            //Opponent Game 1
            Ship ship4 = new Ship("Patrol_Boat", gp2, Arrays.asList("F1", "F2"));
            shipRepo.save(ship4);

            Ship ship5 = new Ship("Destroyer", gp2, Arrays.asList("B5", "C5", "D5"));
            shipRepo.save(ship5);

            /*------------------------------------------------------------------------------------------------------------*/

            //Owner Game 2
            Ship ship6 = new Ship("Patrol_Boat", gp3, Arrays.asList("C6", "C7"));
            shipRepo.save(ship6);

            Ship ship7 = new Ship("Destroyer", gp3, Arrays.asList("B5", "C5", "D5"));
            shipRepo.save(ship7);


            /*------------------------------------------------------------------------------------------------------------*/

            //Opponent Game 2
            Ship ship8 = new Ship("Patrol_Boat", gp4, Arrays.asList("G6", "H6"));
            shipRepo.save(ship8);

            Ship ship9 = new Ship("Submarine", gp4, Arrays.asList("A2", "A3", "A4"));
            shipRepo.save(ship9);

            /*------------------------------------------------------------------------------------------------------------*/

            //Owner Game 3
            Ship ship10 = new Ship("Patrol_Boat", gp5, Arrays.asList("C6", "C7"));
            shipRepo.save(ship10);

            Ship ship11 = new Ship("Destroyer", gp5, Arrays.asList("B5", "C5", "D5"));
            shipRepo.save(ship11);

            /*--------------------------------------------------------------------------------------------------------------*/

            //Opponent Game 3
            Ship ship12 = new Ship("Patrol_Boat", gp6, Arrays.asList("H6", "G6"));
            shipRepo.save(ship12);

            Ship ship13 = new Ship("Submarine", gp6, Arrays.asList("A2", "A3", "A4"));
            shipRepo.save(ship13);

            /*------------------------------------------------------------------------------------------------------------*/

            //Owner Game 4
            Ship ship14 = new Ship("Patrol_Boat", gp7, Arrays.asList("C6", "C7"));
            shipRepo.save(ship14);

            Ship ship15 = new Ship("Destroyer", gp7, Arrays.asList("B5", "C5", "D5"));
            shipRepo.save(ship15);

            /*--------------------------------------------------------------------------------------------------------------*/

            //Opponent Game 4
            Ship ship16 = new Ship("Patrol_Boat", gp8, Arrays.asList("G6", "H6"));
            shipRepo.save(ship16);

            Ship ship17 = new Ship("Submarine", gp8, Arrays.asList("A2", "A3", "A4"));
            shipRepo.save(ship17);

            /*------------------------------------------------------------------------------------------------------------*/

            //Owner Game 5
            Ship ship18 = new Ship("Patrol_Boat", gp9, Arrays.asList("C6", "C7"));
            shipRepo.save(ship18);

            Ship ship19 = new Ship("Destroyer", gp9, Arrays.asList("B5", "C5", "D5"));
            shipRepo.save(ship19);

            /*--------------------------------------------------------------------------------------------------------------*/

            //Opponent Game 5
            Ship ship20 = new Ship("Patrol_Boat", gp10, Arrays.asList("G6", "H6"));
            shipRepo.save(ship20);

            Ship ship21 = new Ship("Submarine", gp10, Arrays.asList("A2", "A3", "A4"));
            shipRepo.save(ship21);

            /*------------------------------------------------------------------------------------------------------------*/

            //Owner Game 6
            Ship ship22 = new Ship("Patrol_Boat", gp11, Arrays.asList("C6", "C7"));
            shipRepo.save(ship22);

            Ship ship23 = new Ship("Destroyer", gp11, Arrays.asList("B5", "C5", "D5"));
            shipRepo.save(ship23);

            /*--------------------------------------------------------------------------------------------------------------*/

            //Opponent Game 6 - No Opponent

            /*------------------------------------------------------------------------------------------------------------*/

            //Game 7 - No players

            /*------------------------------------------------------------------------------------------------------------*/

            //Owner Game 8

            Ship ship24 = new Ship("Patrol_Boat", gp13, Arrays.asList("C6", "C7"));
            shipRepo.save(ship24);

            Ship ship25 = new Ship("Destroyer", gp13, Arrays.asList("B5", "C5", "D5"));
            shipRepo.save(ship25);

            /*--------------------------------------------------------------------------------------------------------------*/

            //Opponent Game 8

            Ship ship26 = new Ship("Patrol_Boat", gp14, Arrays.asList("G6", "H6"));
            shipRepo.save(ship26);

            Ship ship27 = new Ship("Submarine", gp14, Arrays.asList("A2", "A3", "A4"));
            shipRepo.save(ship27);

            /*

            Ship ship1 = new Ship("Patrol_Boat", gp3, Arrays.asList("F1", "F2"));
            shipRepo.save(ship1);

            Ship ship2 = new Ship("Destroyer", gp3, Arrays.asList("B5","C5","D5"));
            shipRepo.save(ship2);

            Ship ship3 = new Ship("Submarine", gp3, Arrays.asList("E1", "F1", "G1"));
            shipRepo.save(ship3);

            Ship ship4 = new Ship("Carrier", gp3, Arrays.asList("H6", "H7", "H8", "H9", "H10"));
            shipRepo.save(ship4);

            Ship ship5 = new Ship("Battleship", gp3, Arrays.asList("E2","E3", "E4", "E5"));
            shipRepo.save(ship5);
*/

            /*------------------------------------------------------------------------------------------------------------*/
            //GAME 1
            Salvo salvo1 = new Salvo(gp1, 1, Arrays.asList("B5", "C5", "F1"));
            salvoRep.save(salvo1);
            Salvo salvo2 = new Salvo(gp2, 1, Arrays.asList("B4", "B5", "B6"));
            salvoRep.save(salvo2);
            Salvo salvo3 = new Salvo(gp1, 2, Arrays.asList("F2", "D5"));
            salvoRep.save(salvo3);
            Salvo salvo4 = new Salvo(gp2, 2, Arrays.asList("E1", "H3", "A2"));
            salvoRep.save(salvo4);

            /*------------------------------------------------------------------------------------------------------------*/
            //GAME 2
            Salvo salvo5 = new Salvo(gp3, 1, Arrays.asList("A2", "A4", "G6"));
            salvoRep.save(salvo5);
            Salvo salvo6 = new Salvo(gp4, 1, Arrays.asList("B5", "D5", "C7"));
            salvoRep.save(salvo6);
            Salvo salvo7 = new Salvo(gp3, 2, Arrays.asList("A3", "H6"));
            salvoRep.save(salvo7);
            Salvo salvo8 = new Salvo(gp4, 2, Arrays.asList("C5", "C6"));
            salvoRep.save(salvo8);

            /*------------------------------------------------------------------------------------------------------------*/
            //GAME 3
            Salvo salvo9 = new Salvo(gp5, 1, Arrays.asList("G6", "H6", "A4"));
            salvoRep.save(salvo9);
            Salvo salvo10 = new Salvo(gp6, 1, Arrays.asList("H1", "H2", "H3"));
            salvoRep.save(salvo10);
            Salvo salvo11 = new Salvo(gp5, 2, Arrays.asList("A2", "A3", "D8"));
            salvoRep.save(salvo11);
            Salvo salvo12 = new Salvo(gp6, 2, Arrays.asList("E1", "F2", "G3"));
            salvoRep.save(salvo12);

            /*------------------------------------------------------------------------------------------------------------*/
            //GAME 4
            Salvo salvo13 = new Salvo(gp7, 1, Arrays.asList("A3", "A4", "F7"));
            salvoRep.save(salvo13);
            Salvo salvo14 = new Salvo(gp8, 1, Arrays.asList("B5", "C6", "D5"));
            salvoRep.save(salvo14);
            Salvo salvo15 = new Salvo(gp7, 2, Arrays.asList("A2", "G6", "H6"));
            salvoRep.save(salvo15);
            Salvo salvo16 = new Salvo(gp8, 2, Arrays.asList("C5", "C7", "D5"));
            salvoRep.save(salvo16);

            /*------------------------------------------------------------------------------------------------------------*/
            //GAME 5
            Salvo salvo17 = new Salvo(gp9, 1, Arrays.asList("A1", "A2", "A3"));
            salvoRep.save(salvo17);
            Salvo salvo18 = new Salvo(gp10, 1, Arrays.asList("B5", "B6", "C7"));
            salvoRep.save(salvo18);
            Salvo salvo19 = new Salvo(gp9, 2, Arrays.asList("G6", "G7", "G8"));
            salvoRep.save(salvo19);
            Salvo salvo20 = new Salvo(gp10, 2, Arrays.asList("C6", "D6", "E6"));
            salvoRep.save(salvo20);
            Salvo salvo21 = new Salvo(gp10, 3, Arrays.asList("H1", "H8"));
            salvoRep.save(salvo21);

            /*------------------------------------------------------------------------------------------------------------*/

            Score score1 = new Score(p1, g1, 1.0);
            scoreRep.save(score1);
            Score score2 = new Score(p2, g1, 0.0);
            scoreRep.save(score2);
            Score score3 = new Score(p1, g2, 0.5);
            scoreRep.save(score3);
            Score score4 = new Score(p2, g2, 0.5);
            scoreRep.save(score4);
            Score score5 = new Score(p2, g3, 1.0);
            scoreRep.save(score5);
            Score score6 = new Score(p4, g3, 0.0);
            scoreRep.save(score6);
            Score score7 = new Score(p2, g4, 0.5);
            scoreRep.save(score7);
            Score score8 = new Score(p1, g4, 0.5);
            scoreRep.save(score8);

        };
    }
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PlayerRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName -> {
            Player player = repository.findByEmail(inputName);
            if (player != null) {
                return new User(player.getEmail(), player.getPassword(),
                        AuthorityUtils.createAuthorityList("USER"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // .antMatchers("/rest/**").hasAnyAuthority("ADMIN")
                .antMatchers("/web/game.html").hasAuthority("USER")
                .antMatchers("/api/game").hasAuthority("USER")
                .antMatchers("/api/gameplayer").hasAuthority("USER")
                .antMatchers("/api/game_view").hasAuthority("USER")
                .antMatchers("/web/games.html").permitAll()
                .antMatchers("/api/players").permitAll()
                .antMatchers("/api/leaderboard").permitAll()
                .antMatchers("/api/games").permitAll()
                .antMatchers("/h2-console/**").permitAll(); //close it when finish game!!!!!

        //  .anyRequest().fullyAuthenticated()

        http.formLogin()
                .usernameParameter("name")
                .passwordParameter("pwd")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        // turn off checking for CSRF tokens
        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        http.headers().frameOptions().disable();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

    }

}



