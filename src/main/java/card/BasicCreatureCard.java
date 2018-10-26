package card;

public class BasicCreatureCard extends BasicCard {
    private int health;
    private int attack;
    private int defense;



    public BasicCreatureCard(int id, String name, String flavourText, String image, int health, int attack, int defense) {
        super(id, name, flavourText, image);
        this.health = health;
        this.attack = attack;
        this.defense = defense;
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



}
