package player;

import card.BasicCard;
import server.Server;

import java.util.List;

public class Player {

    // Fields
    private int id = 1;
    private String name = "player";
    private int health;
    private int mana = 2;
    private List<BasicCard> hand;
    private Server server;

    public Player(int id, String name, int mana, List<BasicCard> hand) {
        this.id = id;
        this.name = name;
        this.health = 10;
        this.mana = mana;
        this.hand = hand;
        this.server = new Server();
    }


    //region Getters & Setters
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
    //endregion

    //region Player Functions
    public void receiveStartCards(){ }

    public void drawCard(){ }

    public void useCard(){ }

    public void placeCard(int handIndex){
        server.receiveCommand("PLACE_CARD " + handIndex);
    }

    public void receiveCard(){ }

    public void attack(int friendlyBoardIndex, int enemyBoardIndex){
        server.receiveCommand("ATTACK " + friendlyBoardIndex + " ON ENEMY_CREATURE " + enemyBoardIndex);
    }

    public int randomizeCreatureHp(){ return 0; }

    public void finishTurn(){ }
    }
    //endregion
