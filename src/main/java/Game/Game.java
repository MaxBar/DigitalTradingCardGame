package Game;

import NetworkClient.NetworkClient;
import card.BasicCard;
import menu.GameMenu;
import player.Player;
import repository.QueryHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game game;
    
    QueryHandler queryHandler;
    private int started;
    private GameMenu gameMenu;
    // Player
    private Player player;
    private List<BasicCard> playerTableCards = new ArrayList<>();
    private int playerGraveyard;
    private int playerDeck;
    private int turn;
    private int round;
    
    // Enemy player
    private List<BasicCard> enemyTableCards;
    private int enemyGraveyard;
    private int enemyHand;
    private int enemyDeck;
    private int enemyHealth;
    private int enemyMana;
    
    private Game() {
        gameMenu = new GameMenu();
        queryHandler = new QueryHandler();
        playerTableCards = new ArrayList<>();
        enemyTableCards = new ArrayList<>();
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
    
    public int getTurn() {
        return turn;
    }
    
    
    public void receiveCommand(String serverOutput) {
        if(serverOutput.startsWith("LOGIN")) {
            login(serverOutput);
        } else if(serverOutput.substring(3).startsWith("DEALT_CARDS") && (Integer.parseInt(serverOutput.substring(1, 2)) == player.getPlayerTurn())) {
            String cards = serverOutput.substring(15);
            String[] chunks = cards.split(", ");
            int[] cardIndices = new int[5];
            for(int i = 0; i < chunks.length; ++i) {
                cardIndices[i] = Integer.parseInt(chunks[i]);
                player.getHand().add(queryHandler.fetchCreatureCardId(cardIndices[i]));
            }
            for(int i = 0; i < player.getHand().size(); ++i) {
                System.out.println(player.getHand().get(i));
            }
        } else if(serverOutput.equals("STARTED")) {
            ++started;
            if(started == 2) {
                startGame();
            }
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
                sendStart();
            }
        } else {
            if(chunks.length > 5) {
                sendStart();
            }
        }
    }
    
    private void sendStart() {
        try {
            NetworkClient.getInstance().sendMessageToServer("STARTED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void startGame() {
        try {
            NetworkClient.getInstance().sendMessageToServer("START_CARDS");
            NetworkClient.getInstance().sendMessageToServer("END_TURN");
            gameMenu.rootMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
