package player;

import card.BasicCard;
import card.BasicCreatureCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    List<BasicCard> playerHand;

    @BeforeEach
    void setUp() {
        playerHand = Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 3, 1, 2),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not excist", 2, 2, 1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 1, 5, 1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 4, 1, 1),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 2, 2, 2));

                player = new Player(1,"Player",10,5, playerHand);
    }

    @Test
    void placeCard() {
        assertTrue(player.placeCard());
        assertFalse(player.placeCard());
    }
}