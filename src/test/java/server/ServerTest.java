/*
package server;

import card.BasicCreatureCard;
import org.junit.jupiter.api.*;
import repository.Database;
import repository.QueryHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


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
        
        
        */
/*assertEquals(0, server.board.getPlayers()[0].getHand().size());
        server.receiveCommand("START_CARDS");
        assertEquals(5, server.board.getPlayers()[0].getHand().size());
        assertEquals(0, server.board.getPlayers()[1].getHand().size());
        server.receiveCommand("START_CARDS");
        assertEquals(5, server.board.getPlayers()[1].getHand().size());*//*

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
    class AttackEnemyPlayerWithCreaturesOnEnemyTable {
    
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
            assertEquals("P" + server.board.checkTurnCombat() + " ATTACK_RESULT_FAILURE CARDS_ON_TABLE", server.getCommand());
            server.receiveCommand("END_TURN");
            server.receiveCommand("ATTACK P1 ENEMY_PLAYER 0");
            assertEquals("P" + server.board.checkTurnCombat() + " ATTACK_RESULT_FAILURE CARDS_ON_TABLE", server.getCommand());
        }
    }
    
    @Nested
    class AttackEnemyPlayerWithConsumedCard {
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
            assertEquals("P" + server.board.getTurn() + " ATTACK_RESULT_FAILURE CREATURE_0_IS_CONSUMED", server.getCommand());
        }
    }
    
    @Nested
    class AttackEnemyPlayerWithoutCreaturesOnEnemyTable {
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
            assertEquals("P" + server.board.getTurn() + " ATTACK_RESULT_SUCCESS P" + server.board.checkTurnCombat() + " HP " + server.board.getPlayers()[server.board.checkTurnCombat()].getHealth(), server.getCommand());
        }
    }
    
    @Nested
    class AttackEnemyPlayerThatDies {
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
            assertEquals("P" + server.board.getTurn() + " ATTACK_RESULT_SUCCESS P" + server.board.checkTurnCombat() + "_DEAD", server.getCommand());
        }
    }
    
    @Nested
    class AttackEnemyCreature {
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
            assertEquals("P" + server.board.getTurn() + "_ATTACK_RESULT_SUCCESS CARD_0 HP " +
                    ((BasicCreatureCard) server.board.getPlayers()[0].getTable().get(0)).getHealth() + " | P" +
                    server.board.checkTurnCombat() + "_CARD_0 hp " + ((BasicCreatureCard) server.board.getPlayers()[1].getTable().get(0)).getHealth(), server.getCommand());
        }
    }
    
    @Nested
    class AttackEnemyCreatureWithWrongCreature {
        @Test
        void aECWWC() {
            server.receiveCommand("P0_DRAW");
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            server.receiveCommand("PLACE P0_CREATURE 0");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[1].setMana(10);
            server.receiveCommand("PLACE P1_CREATURE 0");
            server.receiveCommand("END_TURN");
            ((BasicCreatureCard)server.board.getPlayers()[0].getTable().get(0)).setHealth(10);
            ((BasicCreatureCard)server.board.getPlayers()[1].getTable().get(0)).setHealth(10);
            server.receiveCommand("ATTACK P0 ENEMY_CREATURE 8 0");
            assertEquals("P" +
                    server.board.getTurn() + " ATTACK_RESULT_FAILURE CREATURE_OUT_OF_BOUNDS", server.getCommand());
        }
    }
    
    @Nested
    class AttackEnemyCreatureThatHasNone {
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
            assertEquals("P" +
                    server.board.getTurn() + " ATTACK_RESULT_FAILURE NO_ENEMY_CARDS_ON_TABLE", server.getCommand());
        }
    }
    
    @Nested
    class AttackEnemyCreatureWithConsumed {
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
            assertEquals("P" +
                    server.board.getTurn() + " ATTACK_RESULT_FAILURE CREATURE_0_IS_CONSUMED", server.getCommand());
        }
    }
    
    @Nested
    class CheckPlayerAlive {
        @Test
        void cPA() {
            assertTrue(server.checkPlayerAlive(server.board.getPlayers()[0]));
            server.board.getPlayers()[1].setHealth(0);
            assertFalse(server.checkPlayerAlive(server.board.getPlayers()[1]));
        }
    }

    @Nested
    class CheckCreatureAlive {
        @Test
        void cCA() {
            server.receiveCommand("P0_DRAW");
            server.receiveCommand("END_TURN");
            server.receiveCommand("P1_DRAW");
            server.receiveCommand("END_TURN");
            server.board.getPlayers()[0].setMana(10);
            server.receiveCommand("PLACE P0_CREATURE 0");
            assertTrue(server.checkCreatureAlive(0, 0));
            ((BasicCreatureCard) server.board.getPlayers()[0].getTable().get(0)).setHealth(-1);
            assertFalse(server.checkCreatureAlive(0, 0));
        }
    }
    
    @Nested
    class MoveToGraveyard {
        @Test
        void mTG() {
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
    }
    
    @Nested
    class EndTurn {
        @Test
        void eT() {
            server.receiveCommand("END_TURN");
            assertEquals("ROUND 1 TURN 1", server.getCommand());
            server.receiveCommand("END_TURN");
            assertEquals("ROUND 2 TURN 0", server.getCommand());
        }
    }
    
    @Nested
    class QuitGame {
        @Test
        void qG() {
            server.receiveCommand("QUIT_GAME");
            assertEquals("P" + server.board.getTurn() + " QUIT_GAME", server.getCommand());
        }
    }
}*/
