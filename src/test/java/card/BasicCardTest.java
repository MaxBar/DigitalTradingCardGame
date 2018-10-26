package card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicCardTest {
    BasicCard card = new BasicCard(12, "Marshmallow", "White soft treat", "does not exist yet");

    @Test
    void getId() {
        assertEquals(12, card.id);
    }

    @Test
    void getName() {
        assertEquals("Marshmallow", card.name);
    }

    @Test
    void getFlavourText() {
        assertEquals("White soft treat", card.flavourText);
    }

    @Test
    void getImage() {
        assertEquals("does not exist yet", card.image);
    }
}