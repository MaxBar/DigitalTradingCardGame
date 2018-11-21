package Assets;
public class BasicCreatureCard extends BasicCard {
    private int health;
    private int attack;
    private int defense;
    private int creatureClass;



    public BasicCreatureCard(int id, String name, String flavourText, String image, int manaCost, int health, int attack, int defense, int creatureClass) {
        super(id, name, flavourText, image, manaCost);
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.creatureClass = creatureClass;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public EClass getCreatureClass() {

        if(creatureClass == 0){
            return EClass.MELEE ;
        }else if(creatureClass == 1){
            return EClass.RANGE;
        }else{
            return EClass.SUPPORT;
        }

    }

    public void setCreatureClass(int creatureClass) {
        this.creatureClass = creatureClass;
    }


}