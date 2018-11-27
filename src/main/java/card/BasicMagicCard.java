package card;

public class BasicMagicCard extends BasicCard {
    private String abilityDescription;
    private int abilityValue;
    private EKeyword keyword;

    public BasicMagicCard(int id, String name, String flavourText, String image, int manaCost, EKeyword keyword, String abilityDescription, int abilityValue) {
        super(id, name, flavourText, image, manaCost);
        this.abilityDescription = abilityDescription;
        this.abilityValue = abilityValue;
        this.keyword = keyword;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }

    public void setAbilityDescription(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }

    public int getAbilityValue() {
        return abilityValue;
    }

    public void setAbilityValue(int abilityValue) {
        this.abilityValue = abilityValue;
    }

    public EKeyword getKeyword() {
        return keyword;
    }
}
