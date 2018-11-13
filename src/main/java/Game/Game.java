/*
package Game;

import card.BasicCard;
import player.Player;
import repository.DummyDB;
import server.Server;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game game;
    DummyDB dummy = new DummyDB();
    private List<BasicCard> playerATableCards = new ArrayList<>(); // Remove A when multiplayer
    private List<BasicCard> playerBTableCards = new ArrayList<>(); // Remove when multiplayer
    
    private Player playerA; // Remove A when multiplayer
    private Player playerB; // Remove when multiplayer
    
    private int playerAGraveyard; // Remove A when multiplayer
    private int playerBGraveyard; // Remove when multiplayer
    
    private int playerADeck; // Remove A when multiplayer
    private int playerBDeck; // Remove when multiplayer

    private Game() {

        */
/*playerA = new Player(1, "playerA", 1);
        playerB = new Player(2, "playerB", 1);*//*

    
    }
    
    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }
    
    public List<BasicCard> getPlayerATableCards() {
        return playerATableCards;
    }

    public List<BasicCard> getPlayerBTableCards() {
        return playerBTableCards;
    }
    
    public Player getPlayerA() {
        return playerA;
    }
    
    public Player getPlayerB() {
        return playerB;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public void setPlayerB(Player playerB) {
        this.playerB = playerB;
    }
    
    public int getPlayerAGraveyard() {
        return playerAGraveyard;
    }
    
    public int getPlayerBGraveyard() {
        return playerBGraveyard;
    }
    
    public void incrementPlayerAGraveyard() {
        ++playerAGraveyard;
    }
    
    public void incrementPlayerBGraveyard() {
        ++playerBGraveyard;
    }
    
    public int getPlayerADeck() {
        return playerADeck;
    }
    
    public void setPlayerADeck(int deck) {
        playerADeck = deck;
    }
    
    public int getPlayerBDeck() {
        return playerBDeck;
    }
    
    public void setPlayerBDeck(int deck) {
        playerBDeck = deck;
    }
    
    public void decrementPlayerADeck() {
        --playerADeck;
    }
    
    public void decrementPlayerBDeck() {
        --playerBDeck;
    }
    //TODO add when changing to multiplayer
    */
/*
    private List<BasicCard> EnemyTableCards;
    private int enemyGraveyard;
    private int enemyHand;
    privaet int enemyDeck
    private int enemyHealth;
    private int enemyMana;
    *//*

    
}
*/
