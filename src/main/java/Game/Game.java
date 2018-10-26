package Game;

import card.BasicCard;

import java.util.List;

public class Game {
    private List<BasicCard> playerATableCards; // Remove A when multiplayer
    private List<BasicCard> playerBTableCards; // Remove when multiplayer
    
    private Player playerA; // Remove A when multiplayer
    private Player playerB; // Remove when multiplayer
    
    private int playerAGraveyard; // Remove A when multiplayer
    private int playerBGraveyard; // Remove when multiplayer
    
    private int playerADeck; // Remove A when multiplayer
    private int playerBDeck; // Remove when multiplayer
    
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
    
    public int getPlayerAGraveyard() {
        return playerAGraveyard;
    }
    
    public int getPlayerBGraveyard() {
        return playerBGraveyard;
    }
    
    public int getPlayerADeck() {
        return playerADeck;
    }
    
    public int getPlayerBDeck() {
        return playerBDeck;
    }
    //TODO add when changing to multiplayer
    /*
    private List<BasicCard> EnemyTableCards;
    private int enemyGraveyard;
    private int enemyHand;
    privaet int enemyDeck
    private int enemyHealth;
    private int enemyMana;
    */
    
}
