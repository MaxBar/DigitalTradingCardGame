package server;

import card.BasicCard;
import card.BasicCreatureCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void receiveCommand() {
    }

    Server server = new Server();

    @Test
    void rollDice() {
        for(int i = 0; i <= 10000; ++i) {
            assertThat(server.rollDice(1,6)).isBetween(1, 6);
        }
    }

    @Test
    void dealCards() {
    }

    @Test
    void dealCard() {
    }

    @Test
    void sendCard() {
    }

    @Test
    void placeCard() {
        List<BasicCard> playerA = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 3, 1, 2),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not excist", 2, 2, 1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 1, 5, 1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 4, 1, 1),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 2, 2, 2)));
        List<BasicCard> playerB = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 3, 1, 2),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not excist", 2, 2, 1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 1, 5, 1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 4, 1, 1),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 2, 2, 2)));
        server.players[0].setHand(playerA);
        server.players[1].setHand(playerB);
        server.setTurn(0);
        assertEquals(5, server.players[0].getHand().size());
        assertEquals(0, server.getPlayerATableCards().size());
        server.placeCard(4);
        assertEquals(4, server.players[0].getHand().size());
        assertEquals(1, server.getPlayerATableCards().size());
        
        assertEquals(5, server.players[1].getHand().size());
        server.setTurn(1);
        assertEquals(0, server.getPlayerBTableCards().size());
        server.placeCard(4);
        assertEquals(4, server.players[1].getHand().size());
        assertEquals(1, server.getPlayerBTableCards().size());
    }

    @Test
    void attackEnemyPlayer() {
    }

    @Test
    void attackEnemyCreature() {
    }

    @Test
    void healPlayer() {
    }

    @Test
    void healCreature() {
    }

    @Test
    void checkPlayerAlive() {
        Server server = new Server();
        Player p = new Player(1, "Gary",3, null);
        assertTrue(server.checkPlayerAlive(p));
        p.setHealth(0);
        assertFalse(server.checkPlayerAlive(p));
        p.setHealth(5);
        assertTrue(server.checkPlayerAlive(p));
    }

    @Test
    void checkCreatureAlive() {
    }

    @Test
    void moveToGraveyard() {
    }

    @Test
    void endTurn() {
    }

    @Test
    void quitGame() {
    }
}