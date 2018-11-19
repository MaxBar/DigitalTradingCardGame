package card;

public class ExtraAbilityMagicCard extends BasicMagicCard {
    private int extraAbilityValue;
    private EKeyword keyword;

    public ExtraAbilityMagicCard(int id, String name, String flavourText, String image, int manaCost, EKeyword keyword, String abilityDescription, int abilityValue, int extraAbilityValue) {
        super(id, name, flavourText, image, manaCost, keyword, abilityDescription, abilityValue);
        this.extraAbilityValue = extraAbilityValue;
        this.keyword = keyword;
    }

    public int getExtraAbilityValue() {
        return extraAbilityValue;
    }

    public void setExtraAbilityValue(int extraAbilityValue) {
        this.extraAbilityValue = extraAbilityValue;
    }
}
