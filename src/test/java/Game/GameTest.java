package Game;

import card.BasicCard;
import card.BasicCreatureCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        when(Game.getInstance().getPlayerATableCards()).thenReturn(playerTableCards);
        assertEquals(playerTableCards, Game.getInstance().getPlayerATableCards());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        assertEquals(playerTableCards, Game.getInstance().getPlayerATableCards());
    }
    
    @Test
    void getPlayerBTableCards() {
        when(Game.getInstance().getPlayerBTableCards()).thenReturn(playerTableCards);
        assertEquals(playerTableCards, Game.getInstance().getPlayerBTableCards());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        assertEquals(playerTableCards, Game.getInstance().getPlayerBTableCards());
    }
    
    @Test
    void getPlayerA() {
    }
    
    @Test
    void getPlayerB() {
    }
    
    @Test
    void getPlayerAGraveyard() {
        when(Game.getInstance().getPlayerAGraveyard()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), Game.getInstance().getPlayerAGraveyard());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        when(Game.getInstance().getPlayerAGraveyard()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), Game.getInstance().getPlayerAGraveyard());
    }
    
    @Test
    void getPlayerBGraveyard() {
        when(Game.getInstance().getPlayerBGraveyard()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), Game.getInstance().getPlayerBGraveyard());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        when(Game.getInstance().getPlayerBGraveyard()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), Game.getInstance().getPlayerBGraveyard());
    }
    
    @Test
    void getPlayerADeck() {
        when(Game.getInstance().getPlayerADeck()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), Game.getInstance().getPlayerADeck());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        when(Game.getInstance().getPlayerADeck()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), Game.getInstance().getPlayerADeck());
    }
    
    @Test
    void getPlayerBDeck() {
        when(Game.getInstance().getPlayerBDeck()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), Game.getInstance().getPlayerBDeck());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        when(Game.getInstance().getPlayerBDeck()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), Game.getInstance().getPlayerBDeck());
    }
}

