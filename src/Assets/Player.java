package Assets;

import Assets.BasicCard;
import Assets.BasicCreatureCard;
//import repository.DummyDB;
//import server.Server;

import java.util.ArrayList;
import java.util.List;

public class Player {

    // Fields
    private int id = 1;
    private String name = "player";
    private int health;
    private int mana = 2;
    private List<BasicCard> hand;
    //private Server server = Server.getInstance();
    //private Server server = new Server();

    public Player(int id, String name, int mana) {
        this.id = id;
        this.name = name;
        this.health = 20;
        this.mana = mana;
        this.hand = new ArrayList<>();
        //this.server = new Server();
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

    // Player Functions
   /* public void receiveCard(int id) {
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
    }*/

    public void drawCard() {
    }

    public void useCard() {
    }

    public void placeCard(int handIndex){
       // server.receiveCommand("PLACE_CARD " + handIndex);
    }

    public void attackCreature(int friendlyBoardIndex, int enemyBoardIndex){
       // server.receiveCommand("ATTACK " + friendlyBoardIndex + " ON ENEMY_CREATURE " + enemyBoardIndex);
    }

    public void attackPlayer(int friendlyBoardIndex) {
       // server.receiveCommand("ATTACK " + friendlyBoardIndex + " ON ENEMY_PLAYER");
    }
    public void decreaseMana(int manaCost){

        mana =- manaCost;
        
    }

    public int randomizeCreatureHp() {
        return 0;
    }

    public void finishTurn() {
       // server.receiveCommand("END_TURN");
    }

    public void quitGame() {
       // server.receiveCommand("QUIT_GAME");
    }
}
