package Game;

import NetworkClient.NetworkClient;
import card.BasicCard;
import card.BasicCreatureCard;
import card.SpecialAbilityCreatureCard;
import menu.GameMenu;
import player.Player;
import repository.QueryHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static Game instance = null;
    
    QueryHandler queryHandler;
    private int started = 0;
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
        //gameMenu = new GameMenu();
        queryHandler = new QueryHandler();
        playerTableCards = new ArrayList<>();
        enemyTableCards = new ArrayList<>();
    }
    
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
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
    
    public int getStarted() {
        return started;
    }

    public List<BasicCard> getEnemyTableCards() {
        return enemyTableCards;
    }
    
    public void receiveCommand(String serverOutput) throws IOException {
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
            try {
                gameMenu = new GameMenu();
                gameMenu.rootMenu.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(serverOutput.equals("STARTED")) {
            ++started;
            if(started == 2) {
                startGame();
            }
        } else if(serverOutput.startsWith("ROUND")) {
            endTurn(serverOutput);
        }else if(serverOutput.substring(3).startsWith("PLACE_CREATURE_SUCCESS") && Integer.parseInt(serverOutput.substring(1, 2)) == player.getPlayerTurn()){
            placeSuccess(serverOutput);
        }else if(serverOutput.substring(3).startsWith("PLACE_CREATURE_FAILURE") && Integer.parseInt(serverOutput.substring(1, 2)) == player.getPlayerTurn()) {
            placeFailure(serverOutput);
        }else if(serverOutput.substring(3).startsWith("USE_CREATURE_SUCCESS") && Integer.parseInt(serverOutput.substring(1, 2)) == player.getPlayerTurn()){
            useSuccess(serverOutput);
        }else if(serverOutput.substring(3).startsWith("PLACE_CREATURE_FAILURE") && Integer.parseInt(serverOutput.substring(1, 2)) == player.getPlayerTurn()){
            useFailure(serverOutput);
        }
    }

    private void useSuccess(String serverOutput){
        String[] chunks = serverOutput.split(" ");
        System.out.printf("You used %s\n", player.getHand().get(Integer.parseInt(chunks[2])).getName());
        player.getHand().remove(Integer.parseInt(chunks[2]));
        Game.getInstance().incrementPlayerGraveyard();

    }
    private void useFailure(String serverOutput){
        String[] chunks = serverOutput.split(" ");
        System.out.printf("Not enough mana to use %s", player.getHand().get(Integer.parseInt(chunks[2])).getName());
    }

    private void placeSuccess(String serverOutput){
        int index = Integer.parseInt(serverOutput.substring(26,27));
        if(player.getHand().get(index)instanceof BasicCreatureCard){
            var card = (BasicCreatureCard)player.getHand().get(index);
            player.getHand().remove(index);
            Game.getInstance().getPlayerTableCards().add(card);
        }else if(player.getHand().get(index)instanceof SpecialAbilityCreatureCard){
            var card = (SpecialAbilityCreatureCard)player.getHand().get(index);
            player.getHand().remove(index);
            Game.getInstance().getPlayerTableCards().add(card);
        }
        System.out.printf("You placed %s\n", Game.getInstance().getPlayerTableCards().get(Game.getInstance().getPlayerTableCards().size() - 1).getName());
    }

    private void placeFailure(String serverOutput){
        String[] chunks = serverOutput.split(" ");
        if(chunks[3] == "NO_MANA"){
            System.out.printf("Not enough mana to place %s\n", player.getHand().get(Integer.parseInt(chunks[2])));
        }else if(chunks[3] == "NO_ROOM"){
            System.out.printf("Not enough room on table to place %s\n", player.getHand().get(Integer.parseInt(chunks[2])));
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
            //gameMenu.rootMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void endTurn(String serverOutput) throws IOException {
        String[] chunks = serverOutput.split(" ");
        turn = Integer.parseInt(chunks[3]);
        round = Integer.parseInt(chunks[1]);
        for (BasicCard card : playerTableCards) {
            card.setIsConsumed(false);
        }

        if(round > 0){
            NetworkClient.getInstance().sendMessageToServer(String.format("P%s_DRAW", player.getPlayerTurn()));
        }
    
        //System.out.println("Turn: " + turn);
        //System.out.println("Round: " + round);
    }
}
