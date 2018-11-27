package player;

import card.BasicCard;

import java.util.ArrayList;
import java.util.List;

public class Player {

    // Fields
    private int id;
    private int playerTurn;
    private String name = "player";
    private int health;
    private int mana = 2;
    private List<BasicCard> hand;

    public Player(int id, String name, int playerTurn) {
        this.id = id;
        this.playerTurn = playerTurn;
        this.name = name;
        this.health = 20;
        this.mana = 1;
        this.hand = new ArrayList<>();
    }
  
    // Getters & Setters
    public int getId() {
        return id;
    }
    
    public int getPlayerTurn() {
        return playerTurn;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void changeHealth(int change){
        health += change;
    }
    
    public void decrementHealth(int change) {
        health -= change;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public List<BasicCard> getHand() {
        return hand;
    }

    public void setHand(List<BasicCard> hand) {
        this.hand = hand;
    }
}