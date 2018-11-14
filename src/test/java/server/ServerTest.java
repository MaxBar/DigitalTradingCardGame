package server;

import card.BasicCard;
import card.BasicCreatureCard;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.Database;
import repository.DummyDB;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import card.BasicCreatureCard;
import player.Player;
import repository.QueryHandler;

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
    
    @BeforeEach
    void setUp() {
        Database db = new Database();
        QueryHandler q = new QueryHandler();
        try {
            db.connect();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        server.receiveCommand("LOGIN john@hotmail.se");
        server.receiveCommand("LOGIN linn@hotmail.se");
        server.receiveCommand("STARTED");
        server.receiveCommand("STARTED");
        server.receiveCommand("START_CARDS");
        server.receiveCommand("START_CARDS");
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
        // RUNS IN SETUP, TESTED SO IT WORKS
        
        
        /*assertEquals(0, server.board.getPlayers()[0].getHand().size());
        server.receiveCommand("START_CARDS");
        assertEquals(5, server.board.getPlayers()[0].getHand().size());
        assertEquals(0, server.board.getPlayers()[1].getHand().size());
        server.receiveCommand("START_CARDS");
        assertEquals(5, server.board.getPlayers()[1].getHand().size());*/
    }
    
    @Nested
    class Deal {
        @Test
        void dealCard() {
//        server.receiveCommand("START_CARDS");
//        server.receiveCommand("START_CARDS");
            assertEquals(5, server.board.getPlayers()[0].getHand().size());
            assertEquals(5, server.board.getPlayers()[1].getHand().size());
        
            server.receiveCommand("P0_DRAW");
            assertEquals(6, server.board.getPlayers()[0].getHand().size());
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            assertEquals(6, server.board.getPlayers()[1].getHand().size());
        
        }
    }
    
    @Nested
    class Place {
    
        @Test
        void placeCard() {
            assertEquals(5, server.board.getPlayers()[0].getHand().size());
            assertEquals(5, server.board.getPlayers()[1].getHand().size());
            assertEquals(0, server.board.getPlayers()[0].getTable().size());
            assertEquals(0, server.board.getPlayers()[1].getTable().size());
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            assertEquals(1, server.board.getPlayers()[0].getTable().size());
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[1].setMana(100);
            server.receiveCommand("PLACE P1_CREATURE 0");
            assertEquals(1, server.board.getPlayers()[1].getTable().size());
        }
    }
    
    @Nested
    class attackEnemyPlayerWithCreaturesOnEnemyTable {
    
        @Test
        void aEPWCOET() {
            
            server.receiveCommand("P0_DRAW");
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[1].setMana(10);
            server.receiveCommand("PLACE P1_CREATURE 0");
            server.receiveCommand("END_TURN");
        
            assertEquals(1, server.board.getPlayers()[0].getTable().size());
            assertEquals(1, server.board.getPlayers()[1].getTable().size());
            server.receiveCommand("ATTACK P0 ENEMY_PLAYER 0");
            assertEquals("P1_PLAYER FAIL CARDS_ON_TABLE", server.getCommand());
            server.receiveCommand("END_TURN");
            server.receiveCommand("ATTACK P1 ENEMY_PLAYER 0");
            assertEquals("P0_PLAYER FAIL CARDS_ON_TABLE", server.getCommand());
        }
    }
    
    @Nested
    class attackEnemyPlayerWithConsumedCard {
        @Test
        void aEPWCC() {
            
            server.receiveCommand("P0_DRAW");
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            assertEquals(1, server.board.getPlayers()[0].getTable().size());
            server.receiveCommand("ATTACK P0 ENEMY_PLAYER 0");
            assertEquals("P0 CREATURE " + 0 + " IS_CONSUMED", server.getCommand());
        }
    }
    
    @Nested
    class attackEnemyPlayerWithoutCreaturesOnEnemyTable {
    
        @Test
        void aEPWCOET() {
            
            server.receiveCommand("P0_DRAW");
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            server.receiveCommand("END_TURN");
            server.receiveCommand("END_TURN");
        
            assertEquals(1, server.board.getPlayers()[0].getTable().size());
            server.receiveCommand("ATTACK P0 ENEMY_PLAYER 0");
            assertEquals("P1_PLAYER " + (server.board.getPlayers()[1].getHealth() - ((BasicCreatureCard) server.board.getPlayers()[0].getTable().get(0)).getAttack()), server.getCommand());
        }
    }
    
    @Nested
    class attackEnemyPlayerThatDies {
    
        @Test
        void aEPLTD() {
            
            server.receiveCommand("P0_DRAW");
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            server.receiveCommand("END_TURN");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[1].setHealth(1);
            server.receiveCommand("ATTACK P0 ENEMY_PLAYER 0");
            assertEquals("P1_PLAYER DEAD", server.getCommand());
        }
    }
    
    @Nested
    class attackEnemyCreature {
        @Test
        void aEC() {
            server.receiveCommand("P0_DRAW");
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[1].setMana(10);
            server.receiveCommand("PLACE P1_CREATURE 0");
            server.receiveCommand("END_TURN");
            ((BasicCreatureCard)server.board.getPlayers()[0].getTable().get(0)).setHealth(10);
            ((BasicCreatureCard)server.board.getPlayers()[1].getTable().get(0)).setHealth(10);
            server.receiveCommand("ATTACK P0 ENEMY_CREATURE 0 0");
            assertEquals("ATTACK_RESULT P0_TABLE 0 HP " + ((BasicCreatureCard)server.board.getPlayers()[0].getTable().get(0)).getHealth() +
                    " | P1_TABLE 0 HP " + ((BasicCreatureCard)server.board.getPlayers()[1].getTable().get(0)).getHealth(), server.getCommand());
        }
    }
    
    @Nested
    class attackEnemyCreatureWithWrongCreature {
        @Test
        void aECWWC() {
            server.receiveCommand("P0_DRAW");
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[1].setMana(10);
            server.receiveCommand("PLACE P1_CREATURE 0");
            server.receiveCommand("END_TURN");
            ((BasicCreatureCard)server.board.getPlayers()[0].getTable().get(0)).setHealth(10);
            ((BasicCreatureCard)server.board.getPlayers()[1].getTable().get(0)).setHealth(10);
            server.receiveCommand("ATTACK P0 ENEMY_CREATURE 1 0");
            assertEquals("ATTACK_RESULT WRONG_CREATURE", server.getCommand());
        }
    }
    
    @Nested
    class attackEnemyCreatureThatHasNone {
        @Test
        void aECTHN() {
            server.receiveCommand("P0_DRAW");
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[1].setMana(10);
            server.receiveCommand("END_TURN");
            ((BasicCreatureCard)server.board.getPlayers()[0].getTable().get(0)).setHealth(10);
            server.receiveCommand("ATTACK P0 ENEMY_CREATURE 0 0");
            assertEquals("ATTACK_RESULT FAILED NO_ENEMY_CREATURES", server.getCommand());
        }
    }
    
    @Nested
    class attackEnemyCreatureWithConsumed {
        @Test
        void aECWC() {
            server.receiveCommand("P0_DRAW");
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[1].setMana(10);
            server.receiveCommand("PLACE P1_CREATURE 0");
            server.receiveCommand("ATTACK P1 ENEMY_CREATURE 0 0");
            assertEquals("ATTACK_RESULT FAILED 0 IS_CONSUMED", server.getCommand());
        }
    }

    @Test
    void checkPlayerAlive() {
        assertTrue(server.checkPlayerAlive(server.board.getPlayers()[0]));
        server.board.getPlayers()[1].setHealth(0);
        assertFalse(server.checkPlayerAlive(server.board.getPlayers()[1]));
    }

    @Test
    void checkCreatureAlive() {
        server.receiveCommand("P0_DRAW");
        server.receiveCommand("END_TURN");
        server.receiveCommand("P1_DRAW");
        server.receiveCommand("END_TURN");
        server.board.getPlayers()[0].setMana(10);
        server.receiveCommand("PLACE P0_CREATURE 0");
        assertTrue(server.checkCreatureAlive(0, 0));
        ((BasicCreatureCard)server.board.getPlayers()[0].getTable().get(0)).setHealth(-1);
        assertFalse(server.checkCreatureAlive(0,0));
    }
    
    @Test
    void moveToGraveyard() {
        server.receiveCommand("P0_DRAW");
        server.receiveCommand("END_TURN");
        server.receiveCommand("P1_DRAW");
        server.receiveCommand("END_TURN");
        server.board.getPlayers()[0].setMana(10);
        server.receiveCommand("PLACE P0_CREATURE 0");
        assertEquals(0, server.board.getPlayers()[0].getGraveyard().size());
        server.moveToGraveyard(0, 0);
        assertEquals(1, server.board.getPlayers()[0].getGraveyard().size());
    }
    
    
    @Test
    void endTurn() {
        server.receiveCommand("END_TURN");
        assertEquals("ROUND 1 TURN 1", server.getCommand());
        server.receiveCommand("END_TURN");
        assertEquals("ROUND 2 TURN 0", server.getCommand());
    }
    
    @Test
    void quitGame() {
        server.receiveCommand("QUIT_GAME");
        assertEquals("QUIT_GAME P0", server.getCommand());
    }
}