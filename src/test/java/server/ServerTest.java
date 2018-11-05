package server;

import Game.Game;
import card.BasicCard;
import card.BasicCreatureCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.DummyDB;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import card.BasicCreatureCard;
import player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ServerTest {

    private static Server server = Server.getInstance();
    private static Game game = Game.getInstance();
    private static Player[] players = {new Player(1,"Johan",1), new Player(2,"Linn",1)};

    @BeforeEach
    void setUp() {
        List<BasicCard> playerA = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 1, 1, 2),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not excist", 8, 2, 1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 1, 5, 1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 1, 1, 1)));
        List<BasicCard> playerB = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 1, 1, 2),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not excist", 8, 2, 1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 8, 5, 1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 8, 1, 1)));
        server.setPlayerADeck(playerA);
        server.setPlayerBDeck(playerB);
        for (int i = 0; i < 4; i++) {

            Game.getInstance().getPlayerATableCards().add(playerA.get(i));
            Server.getInstance().getPlayerATableCards().add(playerA.get(i));
            Game.getInstance().getPlayerBTableCards().add(playerB.get(i));
            Server.getInstance().getPlayerBTableCards().add(playerB.get(i));

            //System.out.println(game.getPlayerATableCards().get(i).getName());
        }

        server.setPlayers(players);

        game.setPlayerA(server.getPlayers()[0]);
        game.setPlayerB(server.getPlayers()[1]);
    }

    @Test
    void receiveCommand() {
    }

    @Test
    void rollDice() {
        for(int i = 0; i <= 10000; ++i) {
            assertThat(Server.getInstance().rollDice(1,6)).isBetween(1, 6);
        }
    }

    @Test
    void dealCards() {
        /*List<BasicCard> playerB = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 3, 1, 2),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not excist", 2, 2, 1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 1, 5, 1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 4, 1, 1),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 2, 2, 2)));
        
        Server.getInstance().setPlayerADeck(playerB);
        Server.getInstance().dealCard();
        Server.getInstance().dealCard();
        Server.getInstance().dealCard();
        Server.getInstance().dealCard();
        Server.getInstance().dealCard();
        
        

        verify(Server.getInstance()., times(5)).dealCard();*/
    }

    @Test
    void dealCard() {
        List<BasicCard> deckA = new ArrayList<BasicCard>();

        deckA.add(0, new BasicCard(1,"candy","asd","url"));
        deckA.add(1, new BasicCard(2,"candy","asd","url"));

        deckA.remove(1);
        assertEquals(1,deckA.get(0).id);
        assertEquals(1,deckA.size());

        List<BasicCard> deckB = new ArrayList<BasicCard>();

        deckB.add(0, new BasicCard(1,"candy","asd","url"));
        deckB.add(1, new BasicCard(2,"candy","asd","url"));

        deckB.remove(0);

        assertEquals(1,deckB.size());
        deckB.remove(0);

        assertEquals(0,deckB.size());

    }

    @Test
    void sendCard() {
    }

    @Test
    void placeCard() {

        Server.getInstance().setTurn(0);
        assertEquals(5, server.getPlayers()[0].getHand().size());
        assertEquals(4, server.getPlayerATableCards().size());
        assertEquals("SUCCESS", server.placeCard(4));
        assertEquals("FAIL", server.placeCard(2));
        /*assertEquals(4, Server.getInstance().getPlayers()[0].getHand().size());
        assertEquals(1, Server.getInstance().getPlayerATableCards().size());
        
        assertEquals(5, Server.getInstance().getPlayers()[1].getHand().size());
        Server.getInstance().setTurn(1);
        assertEquals(0, Server.getInstance().getPlayerBTableCards().size());
        Server.getInstance().placeCard(4);
        assertEquals(4, Server.getInstance().getPlayers()[1].getHand().size());
        assertEquals(1, Server.getInstance().getPlayerBTableCards().size());*/
    }

    @Test
    void attackEnemyPlayer() {

        //Player A attacks player B
        for (int i = 0; i < 24; i++){
            server.getPlayerBTableCards().remove(0);
        }
        assertEquals(0, Server.getInstance().getPlayerBTableCards().size());
        //assertTrue(Server.getInstance().attackEnemyPlayer());

        Server.getInstance().getPlayerBTableCards().add( new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 1, 3, 3));
        //assertFalse(Server.getInstance().attackEnemyPlayer());

        assertEquals(10, Server.getInstance().getPlayers()[1].getHealth());
        assertEquals(10, Game.getInstance().getPlayerB().getHealth());

        Server.getInstance().getPlayerBTableCards().clear();
        //assertTrue(Server.getInstance().attackEnemyPlayer());

        assertEquals(10, Server.getInstance().getPlayers()[1].getHealth());
        assertEquals(10, Game.getInstance().getPlayerB().getHealth());



        assertTrue(Server.getInstance().checkPlayerAlive(Server.getInstance().getPlayers()[1]));

        Server.getInstance().attackEnemyPlayer();
        Server.getInstance().attackEnemyPlayer();
        Server.getInstance().attackEnemyPlayer();
        server.attackEnemyPlayer();
        server.attackEnemyPlayer();
        server.attackEnemyPlayer();
        assertFalse(Server.getInstance().checkPlayerAlive(Server.getInstance().getPlayers()[1]));
        Server.getInstance().setTurn(1);

        //Player B attacks player A

        for (int i = 0; i < 24; i++){
            server.getPlayerATableCards().remove(0);
        }

        assertEquals(0, Server.getInstance().getPlayerATableCards().size());
        //assertTrue(Server.getInstance().attackEnemyPlayer());

        Server.getInstance().getPlayerATableCards().add( new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 1, 3, 3));
        //assertFalse(Server.getInstance().attackEnemyPlayer());

        assertEquals(10, Server.getInstance().getPlayers()[0].getHealth());
        assertEquals(10, Game.getInstance().getPlayerA().getHealth());

        Server.getInstance().getPlayerATableCards().clear();
        //assertTrue(Server.getInstance().attackEnemyPlayer());

        assertEquals(10, Server.getInstance().getPlayers()[0].getHealth());
        assertEquals(10, Game.getInstance().getPlayerA().getHealth());



        assertTrue(Server.getInstance().checkPlayerAlive(Server.getInstance().getPlayers()[0]));
        for(int i = 0; i < 8; i++){
            Server.getInstance().attackEnemyPlayer();
        }

        assertFalse(Server.getInstance().checkPlayerAlive(Server.getInstance().getPlayers()[0]));



    }

    @Test
    void attackEnemyCreature() {
       /* List<BasicCard> playerAHand = new ArrayList<>();
        playerAHand.add(new BasicCreatureCard(1,"asd","asd","asd",1,2,2));
        playerAHand.add(new BasicCreatureCard(2,"asd","asd","asd",8,2,2));

        List<BasicCard> playerBHand = new ArrayList<>();
        playerBHand.add(new BasicCreatureCard(2,"asd","asd","asd",1,2,2));
        playerBHand.add(new BasicCreatureCard(1,"asd","asd","asd",8,2,2));
*/
        /*int attackingCreatureIndex = 0;
        int defendingCreatureIndex = 0;
        BasicCreatureCard attackingCreature = (BasicCreatureCard)playerAHand.get(attackingCreatureIndex);
        BasicCreatureCard defendingCreature = (BasicCreatureCard)playerBHand.get(defendingCreatureIndex);

        int playerARoll = 6;
        int playerBRoll = 4;
        int dmg = playerARoll - playerBRoll;
        assertThat(playerARoll).isGreaterThan(playerBRoll);

        defendingCreature.setHealth(defendingCreature.getHealth() - dmg);
        assertEquals(2,defendingCreature.getHealth());

        int playerARoll2 = 2;
        int playerBRoll2 = 4;
        int dmg2 = playerBRoll2 - playerARoll2;
        assertThat(playerARoll2).isLessThan(playerBRoll2);

        attackingCreature.setHealth(attackingCreature.getHealth() - dmg2);
        assertEquals(2,attackingCreature.getHealth());*/

        /*Server.getInstance().setPlayerATableCards(playerAHand);
        Server.getInstance().setPlayerBTableCards(playerBHand);*/

        /*for (int i = 0; i < 2; i++) {
            Game.getInstance().getPlayerATableCards().add(playerAHand.get(i));
            Game.getInstance().getPlayerBTableCards().add(playerBHand.get(i));
        }*/
        assertEquals("SUCCESS A SUCCESS", Server.getInstance().attackEnemyCreature(1, 1));

        assertEquals("SUCCESS DEAD", Server.getInstance().attackEnemyCreature(0,0));

    }

    @Test
    void randomizeCreatureHp() {
        int min = 1;
        int max = 10;
        SecureRandom sRandom = new SecureRandom();
        int hp = sRandom.nextInt(max - min + 1)+ min;
        // int hp = 30;
        assertThat(hp).isLessThan(11);
        assertThat(hp).isGreaterThan(0);
        System.out.println(hp);
    }

    @Test
    void healPlayer() {
    }

    @Test
    void healCreature() {
    }

    @Test
    void checkPlayerAlive() {
        Player p = new Player(1, "Gary",3);
        assertTrue(Server.getInstance().checkPlayerAlive(p));
        p.setHealth(0);
        assertFalse(Server.getInstance().checkPlayerAlive(p));
        p.setHealth(5);
        assertTrue(Server.getInstance().checkPlayerAlive(p));
    }

    @Test
    void checkCreatureAlive() {

        assertTrue(Server.getInstance().checkCreatureAlive(3, 0));
        ((BasicCreatureCard)Server.getInstance().getPlayerATableCards().get(3)).setHealth(-1);
        assertFalse(Server.getInstance().checkCreatureAlive(3,0));
    }
    
    @Test
    void moveToGraveyard() {
        //region code
        /*List<BasicCard> playerA = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 3, 1, 2),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not excist", 2, 2, 1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 1, 5, 1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 4, 1, 1)));
        List<BasicCard> playerB = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 3, 1, 2),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not excist", 2, 2, 1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 1, 5, 1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 4, 1, 1)));
        for (int i = 0; i < 4; i++) {
            Game.getInstance().getPlayerATableCards().add(playerA.get(i));
            Server.getInstance().getPlayerATableCards().add(playerA.get(i));
            Game.getInstance().getPlayerBTableCards().add(playerB.get(i));
            Server.getInstance().getPlayerBTableCards().add(playerB.get(i));
            
            //System.out.println(game.getPlayerATableCards().get(i).getName());
        }*/
        assertEquals(0, Server.getInstance().getPlayerAGraveyard().size());
        assertEquals(0, Server.getInstance().getPlayerBGraveyard().size());
        Server.getInstance().moveToGraveyard(2, 1);
        //verify(server, times(1)).moveToGraveyard(2, 1);
        assertEquals(1, Server.getInstance().getPlayerBGraveyard().size());
        assertEquals(3, Server.getInstance().getPlayerBTableCards().size());
        assertEquals(3, Game.getInstance().getPlayerBTableCards().size());
        assertEquals(1, Game.getInstance().getPlayerBGraveyard());

        Server.getInstance().moveToGraveyard(2, 0);

        //endregion
        assertEquals(1, Server.getInstance().getPlayerAGraveyard().size());
        assertEquals(3, Server.getInstance().getPlayerATableCards().size());
        assertEquals(3, Game.getInstance().getPlayerATableCards().size());
        assertEquals(1, Game.getInstance().getPlayerAGraveyard());

        Server.getInstance().moveToGraveyard(1, 0);
        assertEquals(2, Server.getInstance().getPlayerAGraveyard().size());
        assertEquals(2, Server.getInstance().getPlayerATableCards().size());
        assertEquals(2, Game.getInstance().getPlayerATableCards().size());
        assertEquals(2, Game.getInstance().getPlayerAGraveyard());

        
    }

    @Test
    void endTurn() {
    }

    @Test
    void quitGame() {
    }
}