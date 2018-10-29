package server;

import Game.Game;
import card.BasicCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.DummyDB;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ServerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void receiveCommand() {
    }

    @Test
    void rollDice() {
    }

    @Test
    void dealCards() {
    }

    @Test
    void dealCard() {
        List<BasicCard> deckA = new ArrayList<BasicCard>();

        deckA.add(0, new BasicCard(1,"candy","asd","url"));
        deckA.add(1, new BasicCard(2,"candy","asd","url"));

        assertEquals(1,deckA.get(0).id);
        assertEquals(2,deckA.get(1).id);

        List<BasicCard> deckB = new ArrayList<BasicCard>();

        deckB.add(0, new BasicCard(1,"candy","asd","url"));
        deckB.add(1, new BasicCard(2,"candy","asd","url"));

        assertEquals(1,deckB.get(0).id);
        assertEquals(2,deckB.get(1).id);

    }

    @Test
    void sendCard() {
    }

    @Test
    void placeCard() {
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