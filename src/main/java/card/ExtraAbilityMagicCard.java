package card;

public class ExtraAbilityMagicCard extends BasicMagicCard {
    private int extraAbilityValue;

    public ExtraAbilityMagicCard(int id, String name, String flavourText, String image, int manaCost, String abilityDescription, int abilityValue, int extraAbilityValue) {
        super(id, name, flavourText, image, manaCost, abilityDescription, abilityValue);
        this.extraAbilityValue = extraAbilityValue;
    }

    public int getExtraAbilityValue() {
        return extraAbilityValue;
    }

    public void setExtraAbilityValue(int extraAbilityValue) {
        this.extraAbilityValue = extraAbilityValue;
    }
}
