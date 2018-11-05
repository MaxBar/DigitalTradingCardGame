package Game;

import card.BasicCard;
import card.BasicCreatureCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.Server;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GameTest {
    ArrayList<BasicCard> playerTableCards = new ArrayList<BasicCard>();
    
    @BeforeEach
    void setUp() {
        BasicCreatureCard card1 = new BasicCreatureCard(1, "Hallon", "hej", "qwerty", 5, 5, 5);
        BasicCreatureCard card2 = new BasicCreatureCard(2, "Saft", "då", "asdf", 1, 1, 1);
        playerTableCards.add(card1);
        playerTableCards.add(card2);
    }
    
    @Test
    void getPlayerATableCards() {

    }
    
    @Test
    void getPlayerBTableCards() {

    }
    
    @Test
    void getPlayerA() {
    }
    
    @Test
    void getPlayerB() {
    }
    
    @Test
    void getPlayerAGraveyard() {

    }
    
    @Test
    void getPlayerBGraveyard() {

    }
    
    @Test
    void getPlayerADeck() {

    }
    
    @Test
    void getPlayerBDeck() {

    }
}

