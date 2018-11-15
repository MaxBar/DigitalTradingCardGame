package player;

import card.BasicCard;
import card.BasicCreatureCard;
import repository.DummyDB;

import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
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
        deck = new ArrayList<>();/*(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 0, 1, 2, 1, 2),
                new BasicCreatureCard(2, "Plopp", "Chocolate with gooey caramel center", "does not exist", 0, 2, 1, 1, 2),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 0, 5, 1, 1, 2),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 0, 1, 1, 1, 2),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 0, 2, 2, 1, 2),
                new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 0, 3, 3, 1, 2),
                new BasicCreatureCard(7, "Daim", "Chocolate with hard filling", "does not exist yet", 0, 3, 5, 1, 2),
                new BasicCreatureCard(8, "Bounty", "Chocolate with coconut filling", "does not exist yet", 0, 2, 1, 1, 2),
                new BasicCreatureCard(9, "Hubba Bubba", "Sweet chewing-gum", "does not exist yet", 0, 3, 3, 1, 2),
                new BasicCreatureCard(10, "Raisin", "Dried up grapes pretending to be candy", "does not exist yet", 0, 1, 1, 1, 2))
        );*/
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

    /*// Player Functions
    public void receiveCard(int id) {
        DummyDB db = new DummyDB();

        for (BasicCard card: db.database) {
            if(id == card.getId()){
                hand.add(card);
            }

        }

    }

    public void receiveStartCards(int[] arrayOfId) {
            for (int j = 0; j < 5; j++){
                receiveCard(arrayOfId[j]);
            }
    }

    public void drawCard() {
    }

    public void useCard() {
    }

    public void placeCard(int handIndex){
        server.receiveCommand("PLACE_CARD " + handIndex);
    }

    public void attackCreature(int friendlyBoardIndex, int enemyBoardIndex){
        server.receiveCommand("ATTACK " + friendlyBoardIndex + " ON ENEMY_CREATURE " + enemyBoardIndex);
    }

    public void attackPlayer(int friendlyBoardIndex) {
        server.receiveCommand("ATTACK " + friendlyBoardIndex + " ON ENEMY_PLAYER");
    }


    public int randomizeCreatureHp() {
        return 0;
    }

    public void finishTurn() {
        server.receiveCommand("END_TURN");
    }

    public void quitGame() {
        server.receiveCommand("QUIT_GAME");
    }*/

    public void decreaseMana(int manaCost){

        mana -= manaCost;

    }
}
