package board;

import card.BasicCard;
import card.BasicCreatureCard;
import player.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    public final int PLAYER_A = 0;
    public final int PLAYER_B = 1;
    public final int maxTableSize = 5;
    private int turn;
    private int round;
    private Player[] players;/*
    private List<BasicCard> playerADeck;
    private List<BasicCard> playerBDeck;
    private List<BasicCard> playerAGraveyard;
    private List<BasicCard> playerBGraveyard;
    private List<BasicCard> playerATableCards = new ArrayList<>();
    private List<BasicCard> playerBTableCards = new ArrayList<>();*/

    //Game game = Game.getInstance();

    public Board(){
        round = 0;
        turn = 0;
        players = new Player[2];
        /*playerADeck = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 0, 1, 2, 1, 2),
                new BasicCreatureCard(2, "Plopp", "Chocolate with gooey caramel center", "does not exist", 0, 2, 1, 1, 2),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 0, 5, 1, 1, 2),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 0, 1, 1, 1, 2),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 0, 2, 2, 1, 2),
                new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 0, 3, 3, 1, 2),
                new BasicCreatureCard(7, "Daim", "Chocolate with hard filling", "does not exist yet", 0, 3, 5, 1, 2),
                new BasicCreatureCard(8, "Bounty", "Chocolate with coconut filling", "does not exist yet", 0, 2, 1, 1, 2),
                new BasicCreatureCard(9, "Hubba Bubba", "Sweet chewing-gum", "does not exist yet", 0, 3, 3, 1, 2),
                new BasicCreatureCard(10, "Raisin", "Dried up grapes pretending to be candy", "does not exist yet", 0, 1, 1, 1, 2))
        );

        playerBDeck = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 0, 1, 2, 1, 2),
                new BasicCreatureCard(2, "Plopp", "Chocolate with gooey caramel center", "does not excist", 0, 2, 1, 1, 2),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 0, 5, 1, 1, 2),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 0, 1, 1, 1, 2),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 0, 2, 2, 1, 2),
                new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 0, 3, 3, 1, 2),
                new BasicCreatureCard(7, "Daim", "Chocolate with hard filling", "does not exist yet", 0, 3, 5, 1, 2),
                new BasicCreatureCard(8, "Bounty", "Chocolate with coconut filling", "does not exist yet", 0, 2, 1, 1, 2),
                new BasicCreatureCard(9, "Hubba Bubba", "Sweet chewing-gum", "does not exist yet", 0, 3, 3, 1, 2),
                new BasicCreatureCard(10, "Raisin", "Dried up grapes pretending to be candy", "does not exist yet", 0, 1, 1, 1, 2))
        );*/
        /*playerAGraveyard = new ArrayList<>();
        playerBGraveyard = new ArrayList<>();
        playerATableCards = new ArrayList<>();
        playerBTableCards = new ArrayList<>();*/
    }
    
    public int getTurn() {
        return turn;
    }
    
    public void setTurn(int turn) {
        if(this.turn == 0) {
            this.turn = turn;
        } else {
            this.turn = turn;
            ++round;
        }
    }
    
    public void increaseTurn(int increase) {
        if(turn == 0) {
            turn += increase;
        } else {
            turn = 0;
            ++round;
        }
    }
    
    public int getRound() {
        return round;
    }
    
    public void setRound(int round) {
        this.round = round;
    }
    
    public void increaseRound() {
        round += 1;
    }
    
    public Player[] getPlayers() {
        return players;
    }
    
    public void setPlayers(Player[] players) {
        this.players = players;
    }
    
    public void addPlayer(Player player, int pos) {
        players[pos] = player;
    }
    
    /*public List<BasicCard> getPlayerADeck() {
        return playerADeck;
    }
    
    public void setPlayerADeck(List<BasicCard> playerADeck) {
        this.playerADeck = playerADeck;
    }
    
    public List<BasicCard> getPlayerBDeck() {
        return playerBDeck;
    }
    
    public void setPlayerBDeck(List<BasicCard> playerBDeck) {
        this.playerBDeck = playerBDeck;
    }
    
    public List<BasicCard> getPlayerAGraveyard() {
        return playerAGraveyard;
    }
    
    public void setPlayerAGraveyard(List<BasicCard> playerAGraveyard) {
        this.playerAGraveyard = playerAGraveyard;
    }
    
    public List<BasicCard> getPlayerBGraveyard() {
        return playerBGraveyard;
    }
    
    public void setPlayerBGraveyard(List<BasicCard> playerBGraveyard) {
        this.playerBGraveyard = playerBGraveyard;
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
    }*/
}
