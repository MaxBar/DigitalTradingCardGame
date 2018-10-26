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
    Player player = new Player(1, "player", 10,hand);


    @Test
    void receiveCardTest() {
        DummyDB db = new DummyDB();


        int id = 1;
        for (BasicCard card: db.database) {
            if(id == card.getId()){
                hand.add(card);
            }
        }

        assertEquals(1, hand.size());

    }

    @Test
    void receiveStartCardsTest() {
    }
}