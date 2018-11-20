package Assets;

import Assets.BasicCard;
import Assets.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game game;
    private int enemyGraveyard;
    private int enemyHand;
    private int enemyDeck;
    private int enemyHealth;
    private int enemyMana;
    private List<BasicCard> playerTableCards = new ArrayList<>();
    private List<BasicCard> EnemyTableCards = new ArrayList<>();

    private Player player;

    
    private int playerGraveyard;

    
    private int playerDeck;


    private Game() {

    
    }
    
    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }
    
    public List<BasicCard> getPlayerTableCards() {
        return playerTableCards;
    }
    
    public Player getPlayerA() {
        return player;
    }

    public void setPlayer(Player playerA) {
        this.player = playerA;
    }

    public int getPlayerGraveyard() {
        return playerGraveyard;
    }
    
    public void incrementPlayerGraveyard() {
        ++playerGraveyard;
    }
    
    public int getPlayerDeck() {
        return playerDeck;
    }
    
    public void setPlayerDeck(int deck) {
        playerDeck = deck;
    }

    public void decrementPlayerDeck() {
        --playerDeck;
    }


    
}
