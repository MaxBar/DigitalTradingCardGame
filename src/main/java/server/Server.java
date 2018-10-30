package server;

import card.BasicCard;
import card.BasicCreatureCard;
import player.Player;
import Game.Game;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Server {

    // Fields
    private static Server server = null;
    private int round;
    public Player[] players;
    private String command;
    private int turn;
    private List<BasicCard> playerADeck;
    private List<BasicCard> playerBDeck;
    private List<BasicCard>[] playerDecks;
    private List<BasicCard> playerAGraveyard = new ArrayList<>();
    private List<BasicCard> playerBGraveyard = new ArrayList<>();
    private List<BasicCard>[] playerGraveyards;
    
    private Server() {
        players = new Player[2];
        players[0] = new Player(1, "playerA", 1, null);
        players[1] = new Player(2, "playerB", 1, null);
    }
    
    public static Server getInstance() {
        if (server == null) {
            server = new Server();
        }
        return server;
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
    
    private List<BasicCard> playerATableCards = new ArrayList<BasicCard>();
    private List<BasicCard> playerBTableCards = new ArrayList<BasicCard>();

    //region Getters & Setters
    public int getRound() { return round; }

    public void setRound(int round) { this.round = round; }

    public String getCommand() { return command; }

    public void setCommand(String command) { this.command = command; }

    public int getTurn() { return turn; }

    public void setTurn(int turn) {
        this.turn = turn; }

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
        return sRandom.nextInt(max - min + 1)+ min;
    }

    public int[] dealCards() {

        int ids[] = new int[5];
        for (int i = 0; i < 5; i++) {
            ids[i] = dealCard();
        }
        return ids;
    }

    public int dealCard() {

        int id;
        if (turn == 0) {
            id = playerADeck.get(0).id;
            playerADeck.remove(0);
            return id;
        }
        id = playerBDeck.get(0).id;
        playerBDeck.remove(0);
        return id;
    }

    public String sendCard(String s) {
        return "";
    }
    
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

    public boolean attackEnemyPlayer() {
        if(turn == 0 ){
            if(playerBTableCards.size() == 0){
                int health = -rollDice(1,6);
                players[1].changeHealth(health);
                Game.getInstance().getPlayerB().changeHealth(health);

                //Should we return a string, example: SUCCESS PLAYER ALIVE/SUCCESS PLAYER DEAD?

                return true;

            }
            return false;
        }
        else {
            if(playerATableCards.size() == 0) {
                int health = -rollDice(1,6);
                players[0].changeHealth(health);
                Game.getInstance().getPlayerA().changeHealth(health);
                return true;
            }

            return false;
        }
    }

    public String attackEnemyCreature(BasicCreatureCard playerACreature, BasicCreatureCard playerBCreature) {
        int playerARoll;
        int playerBRoll;
        int dmg;
        String attackMsg = "Attack succeeded, creature is still alive";
        String successMsg ="Attack succeeded, creature died";

        do{
            playerARoll = Server.getInstance().rollDice(1, 6);
            playerBRoll = Server.getInstance().rollDice(1, 6);
        } while(playerARoll == playerBRoll);

        if (playerARoll > playerBRoll) {
            dmg = playerARoll - playerBRoll;
            playerBCreature.setHealth((playerBCreature.getHealth() - dmg));
            return !checkCreatureAlive(playerBCreature) ? successMsg : attackMsg;
        } else{
            dmg = playerBRoll - playerARoll;
            playerACreature.setHealth((playerACreature.getHealth() - dmg));
            checkCreatureAlive(playerACreature);
            return !checkCreatureAlive(playerACreature) ? successMsg : attackMsg;
        }
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

    public boolean checkCreatureAlive(BasicCreatureCard creature) {
        return creature.getHealth() > 0;
    }
    
    public void moveToGraveyard(int index, int player) {
        if (player == 0) {
            BasicCard card = playerATableCards.get(index);

            playerAGraveyard.add(card);
            playerATableCards.remove(index);

            Game.getInstance().getPlayerATableCards().remove(index);
            Game.getInstance().incrementPlayerAGraveyard();

        } else {
            BasicCard card = playerBTableCards.get(index);
            playerBGraveyard.add(card);
            playerBTableCards.remove(index);
            Game.getInstance().getPlayerBTableCards().remove(index);
            Game.getInstance().incrementPlayerBGraveyard();


        }
        //System.out.println(playerBTableCards.get(index));
    }

    public void endTurn() {
        turn = turn == 0 ? 1 : 0;
        round++;
    }

    public void quitGame() {
        System.exit(0);
    }

}
