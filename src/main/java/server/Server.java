package server;

import card.BasicCard;
import player.Player;
import Game.Game;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Server {

    // Fields
    private int round;
    public Player[] players;
    private String command;
    private int turn;
    private List<BasicCard> playerADeck;
    private List<BasicCard> playerBDeck;
    private List<BasicCard>[] playerDecks;
    private List<BasicCard> playerAGraveyard;
    private List<BasicCard> playerBGraveyard;
    private List<BasicCard>[] playerGraveyards;
    
    public Server() {
        players = new Player[2];
        players[0] = new Player(1, "playerA", 1, null);
        players[1] = new Player(2, "playerB", 1, null);
    }
    
    public List<BasicCard> getPlayerATableCards() {
        return playerATableCards;
    }
    
    public void setPlayerATableCards(List<BasicCard> playerATableCards) {
        this.playerATableCards = playerATableCards;
    }
    
    public List<BasicCard> getPlayerBTableCards() {
        return playerBTableCards;
    }
    
    public void setPlayerBTableCards(List<BasicCard> playerBTableCards) {
        this.playerBTableCards = playerBTableCards;
    }
    
    private List<BasicCard> playerATableCards = new ArrayList<>();
    private List<BasicCard> playerBTableCards = new ArrayList<>();

    //region Getters & Setters
    public int getRound() { return round; }

    public void setRound(int round) { this.round = round; }

    public String getCommand() { return command; }

    public void setCommand(String command) { this.command = command; }

    public int getTurn() { return turn; }

    public void setTurn(int turn) { this.turn = turn; }

    public List<BasicCard> getPlayerADeck() { return playerADeck; }

    public void setPlayerADeck(List<BasicCard> playerADeck) { this.playerADeck = playerADeck; }

    public List<BasicCard> getPlayerBDeck() { return playerBDeck; }

    public void setPlayerBDeck(List<BasicCard> playerBDeck) { this.playerBDeck = playerBDeck; }

    public List<BasicCard>[] getPlayerDecks() { return playerDecks; }

    //public void setPlayerDecks(List<BasicCard> playerDecks) { this.playerDecks = playerDecks; }

    public List<BasicCard> getPlayerAGraveyard() { return playerAGraveyard; }

    public void setPlayerAGraveyard(List<BasicCard> playerAGraveyard) { this.playerAGraveyard = playerAGraveyard; }

    public List<BasicCard> getPlayerBGraveyard() { return playerBGraveyard; }

    //public void setPlayerBGraveyard(List<BasicCard> playerBGraveyard) { this.playerBGraveyard = playerBGraveyard; }

    public List<BasicCard>[] getPlayerGraveyards() { return playerGraveyards; }

    //public void setPlayerGraveyards(List<BasicCard> playerGraveyards) { this.playerGraveyards = playerGraveyards; }
    //endregion

    // server.Server functions
    public String receiveCommand(String s) {
        return "";
    }

    SecureRandom sRandom = new SecureRandom();

    public int rollDice(int min, int max) {
        return sRandom.nextInt(max)+ min;
    }

    public String dealCards(String s) {
        return "";
    }

    public String dealCard(String s) {
        return "";
    }

    public String sendCard(String s) {
        return "";
    }
    
    private Game game = new Game();
    
    public void placeCard(int index) {
        if(turn == 0) {
            BasicCard card = players[0].getHand().get(index);
            players[0].getHand().remove(index);
            playerATableCards.add(card);
        } else {
            BasicCard card = players[1].getHand().get(index);
            players[1].getHand().remove(index);
            playerBTableCards.add(card);
        }
    }

    public String attackEnemyPlayer(String s) {
        return "";
    }

    public String attackEnemyCreature(String s) {
        return "";
    }

    public String healPlayer(String s) {
        return "";
    }

    public String healCreature(String s) {
        return "";
    }

    public boolean checkPlayerAlive(Player p) {
        return p.getHealth() > 0;
    }

    public String checkCreatureAlive(String s) {
        return "";
    }

    public String moveToGraveyard(String s) {
        return "";
    }

    public String endTurn(String s) {
        return "";
    }

    public String quitGame(String s) {
        return "";
    }

}
