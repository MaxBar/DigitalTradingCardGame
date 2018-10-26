package player;

import card.BasicCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.DummyDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerTest {
    Player player = new Player(1, "player", 10,2);

    @Mock
    List<BasicCard> hand;

    @Test
    void receiveCardTest() {
        DummyDB db = new DummyDB();
        int id =1;

        hand.add(db.database.get(id));


        when(hand.size()).thenReturn(1);
        assertTrue(hand.size()==1);

    }

    @Test
    void receiveStartCardsTest() {
    }
}