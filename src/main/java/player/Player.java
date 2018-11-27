package player;

import card.BasicCard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    
    // Fields
    private int id;
    private String name;
    private int health;
    private int mana;
    private List<BasicCard> hand;
    private List<BasicCard> table;
    private List<BasicCard> graveyard;
    
    private List<BasicCard> deck;
    
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.health = 20;
        this.mana = 1;
        this.hand = new ArrayList<>();
        this.table = new ArrayList<>();
        this.graveyard = new ArrayList<>();
        deck = new ArrayList<>();
        Collections.shuffle(deck);
    }
    
    // Getters & Setters
    public int getId() {
        return id;
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
    
    public List<BasicCard> getTable() {
        return table;
    }
    
    public void setTable(List<BasicCard> table) {
        this.table = table;
    }
    
    public List<BasicCard> getGraveyard() {
        return graveyard;
    }
    
    public void setGraveyard(List<BasicCard> graveyard) {
        this.graveyard = graveyard;
    }
    
    public List<BasicCard> getDeck() {
        return deck;
    }
    
    public void setDeck(List<BasicCard> deck) {
        this.deck = deck;
    }

    public void decreaseMana(int manaCost){

        mana -= manaCost;

    }
}
