package player;

import card.BasicCard;

import java.util.List;

public class Player {

    // Fields
    private int id;
    private String name;
    private int health;
    private int mana;
    private List<BasicCard> hand;

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getMana() { return mana; }
    public void setMana(int mana) { this.mana = mana; }
    public List<BasicCard> getHand() { return hand; }
    public void setHand(List<BasicCard> hand) { this.hand = hand; }

    // Player Functions
    public void receiveStartCards(){ }
    public void drawCard(){ }
    public void useCard(){ }
    public void placeCard(){ }
    public void receiveCard(){ }
    public void attack(){ }
    public int randomizeCreatureHp(){ return 0; }
    public void finishTurn(){ }
}
