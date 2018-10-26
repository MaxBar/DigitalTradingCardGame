
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
    
    ArrayList<BasicCard> playerTableCards = new ArrayList<>();
    
    @Mock
    Game game = new Game();
    
    @BeforeEach
    void setUp() {
        BasicCreatureCard card1 = new BasicCreatureCard(1, "Hallon", "hej", "qwerty", 5, 5, 5);
        BasicCreatureCard card2 = new BasicCreatureCard(2, "Saft", "då", "asdf", 1, 1, 1);
        playerTableCards.add(card1);
        playerTableCards.add(card2);
    }
    
    @Test
    void getPlayerATableCards() {
        when(game.getPlayerATableCards()).thenReturn(playerTableCards);
        assertEquals(playerTableCards, game.getPlayerATableCards());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        assertEquals(playerTableCards, game.getPlayerATableCards());
    }
    
    @Test
    void getPlayerBTableCards() {
        when(game.getPlayerBTableCards()).thenReturn(playerTableCards);
        assertEquals(playerTableCards, game.getPlayerBTableCards());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        assertEquals(playerTableCards, game.getPlayerBTableCards());
    }
    
    @Test
    void getPlayerA() {
    }
    
    @Test
    void getPlayerB() {
    }
    
    @Test
    void getPlayerAGraveyard() {
        when(game.getPlayerAGraveyard()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), game.getPlayerAGraveyard());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        when(game.getPlayerAGraveyard()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), game.getPlayerAGraveyard());
    }
    
    @Test
    void getPlayerBGraveyard() {
        when(game.getPlayerBGraveyard()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), game.getPlayerBGraveyard());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        when(game.getPlayerBGraveyard()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), game.getPlayerBGraveyard());
    }
    
    @Test
    void getPlayerADeck() {
        when(game.getPlayerADeck()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), game.getPlayerADeck());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        when(game.getPlayerADeck()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), game.getPlayerADeck());
    }
    
    @Test
    void getPlayerBDeck() {
        when(game.getPlayerBDeck()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), game.getPlayerBDeck());
        playerTableCards.add(new BasicCreatureCard(3, "Lakrits", "ses", "abc", 2, 2, 2));
        when(game.getPlayerBDeck()).thenReturn(playerTableCards.size());
        assertEquals(playerTableCards.size(), game.getPlayerBDeck());
    }
}
