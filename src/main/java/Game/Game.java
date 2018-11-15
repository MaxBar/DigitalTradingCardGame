package Game;

import card.BasicCard;
import player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game game;
    // Player
    private Player player;
    private List<BasicCard> playerTableCards = new ArrayList<>();
    private int playerGraveyard;
    private int playerDeck;
    private int turn;
    private int round;
    
    // Enemy player
    private List<BasicCard> EnemyTableCards;
    private int enemyGraveyard;
    private int enemyHand;
    private int enemyDeck;
    private int enemyHealth;
    private int enemyMana;

    private Game() {
        turn = 0;
        round = 0;
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
    
    public Player getPlayer() {
        return player;
    }

    /*public void setPlayer(Player player) {
        this.player = player;
    }*/
    
    public int getPlayerGraveyard() {
        return playerGraveyard;
    }
    
    public void incrementPlayerGraveyard() {
        ++playerGraveyard;
    }
    
    public int getPlayerDeck() {
        return playerDeck;
    }
    
    public void incrementPlayerDeck() {
        ++playerDeck;
    }
    
    public void decrementPlayerDeck() {
        --playerDeck;
    }
    
    public void setPlayerDeck(int deck) {
        playerDeck = deck;
    }
    
    public void receiveCommand(String serverOutput) {
        if(serverOutput.startsWith("LOGIN")) {
            int playerId = Integer.parseInt(serverOutput.substring(9, serverOutput.indexOf(" ", 9)));
            int playerTurn = Integer.parseInt(serverOutput.substring(18, serverOutput.indexOf(" ", 18)));
            String playerName = serverOutput.substring(25);//, serverOutput.indexOf(" ", 25));
            player = new Player(playerId, playerName, playerTurn);
            System.out.printf("%s %s %s", player.getId(), player.getName(), player.getPlayerTurn());
        }
    }
}
