package card;

public class BasicCreatureCard extends BasicCard {
    private int health;
    private int attack;
    private int defence;



    public BasicCreatureCard(int id, String name, String flavourText, String image, int health, int attack, int defence) {
        super(id, name, flavourText, image);
        this.health = health;
        this.attack = attack;
        this.defence = defence;
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

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }



}
