package Game;

import NetworkClient.NetworkClient;
import card.BasicCard;
import player.Player;

import java.io.IOException;
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
            login(serverOutput);
        }
    }
    
    private void login(String serverOutput) {
        String [] chunks = serverOutput.split (" ");
        String start = "";
        if(chunks[1].equals("john@hotmail.se")) {
            int playerId = Integer.parseInt(chunks[2].substring(3));
            int playerTurn = Integer.parseInt(chunks[3].substring(7));
            String playerName = chunks[4].substring(5);
            player = new Player(playerId, playerName, playerTurn);
            System.out.printf("%s %s %s", player.getId(), player.getName(), player.getPlayerTurn());
            if(chunks.length > 5) {
                startGame();
            }
        } else {
            if(chunks.length > 5) {
                startGame();
            }
        }
    }
    
    private void startGame() {
        try {
            NetworkClient.getInstance().sendMessageToServer("STARTED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
