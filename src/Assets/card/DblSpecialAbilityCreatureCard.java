package Assets;
public class DblSpecialAbilityCreatureCard extends SpecialAbilityCreatureCard {
    int dblAbilityValue;


    public DblSpecialAbilityCreatureCard(int id, String name, String flavourText, String image, int manaCost, int health, int attack, int defense, int creatureClass, String abilityDescription, int abilityValue, int dblAbilityValue) {
        super(id, name, flavourText, image, manaCost, health, attack, defense, creatureClass, abilityDescription, abilityValue);
        this.dblAbilityValue = dblAbilityValue;
    }

    public int getDblAbilityValue() {
        return dblAbilityValue;
    }

    public void setDblAbilityValue(int dblAbilityValue) {
        this.dblAbilityValue = dblAbilityValue;
    }
}
