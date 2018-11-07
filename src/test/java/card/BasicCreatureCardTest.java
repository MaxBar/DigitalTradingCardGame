
package card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicCreatureCardTest {
    BasicCreatureCard card = new BasicCreatureCard(10,"Plopp","Chocolate with gooey caramel center", "does not excist", 2, 4,1,1,2);

    @Test
    void getHealth() {
        assertEquals(2, card.getHealth());
    }

    @Test
    void setHealth() {
        card.setHealth(2);
        assertTrue(card.getHealth() == 2);
    }

    @Test
    void getAttack() {
        assertEquals(4, card.getAttack());
    }

    @Test
    void setAttack() {
        card.setAttack(3);
        assertEquals(3, card.getAttack());
    }

    @Test
    void getDefense() {
        assertEquals(1, card.getDefense());

    }

    @Test
    void setDefense() {
        card.setDefense(5);
        assertEquals(5, card.getDefense());
    }
}
