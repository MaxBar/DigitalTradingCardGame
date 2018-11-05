package player;

import card.BasicCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.DummyDB;

import java.util.ArrayList;
import card.BasicCreatureCard;
import org.junit.jupiter.api.BeforeEach;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PlayerTest {
    List<BasicCard> hand  = new ArrayList<BasicCard>();
    Player player = new Player(1, "player", 10);


    @Test
    void receiveCardTest() {
        DummyDB db = new DummyDB();
        for (int i = 0; i < 1; i++){
            hand.add(db.database.get(0));
        }


        assertEquals(1, hand.size());

    }

    @Test
    void receiveStartCardsTest() {
        for( int i = 0; i <= 5; i++){
            player.receiveCard(i);
        }

        assertEquals(5, player.getHand().size());
    }
}