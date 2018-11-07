package card;

public class BasicMagicCard extends BasicCard {
    String abilityDescription;
    int abilityValue;

    public BasicMagicCard(int id, String name, String flavourText, String image, int manaCost, String abilityDescription, int abilityValue) {
        super(id, name, flavourText, image, manaCost);
        this.abilityDescription = abilityDescription;
        this.abilityValue = abilityValue;
    }
}
