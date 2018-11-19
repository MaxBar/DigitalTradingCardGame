package card;

public class SpecialAbilityCreatureCard extends BasicCreatureCard {


    String abilityDescription;
    int abilityValue;
    EKeyword keyword;

    public SpecialAbilityCreatureCard(int id, String name, String flavourText, String image, int manaCost, int health, int attack, int defense, int creatureClass, EKeyword keyword, String abilityDescription, int abilityValue) {
        super(id, name, flavourText, image, manaCost, health, attack, defense, creatureClass);
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
