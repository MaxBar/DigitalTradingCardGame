package card;

public class ExtraAbilityMagicCard extends BasicMagicCard {
    int extraAbilityValue;

    public ExtraAbilityMagicCard(int id, String name, String flavourText, String image, int manaCost, String abilityDescription, int abilityValue, int extraAbilityValue) {
        super(id, name, flavourText, image, manaCost, abilityDescription, abilityValue);
        this.extraAbilityValue = extraAbilityValue;
    }
}
