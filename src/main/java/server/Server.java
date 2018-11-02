package server;

import card.BasicCard;
import card.BasicCreatureCard;
import player.Player;
import Game.Game;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {

    // Fields
    private static Server server = null;
    public final int PLAYER_A = 0;
    public final int PLAYER_B = 1;
    private int round;
    private int maxTableSize;
    private Player[] players;
    private String command;
    private int turn;
    private List<BasicCard> playerADeck;
    private List<BasicCard> playerBDeck;
    //private List<BasicCard>[] playerDecks;
    private List<BasicCard> playerAGraveyard;
    private List<BasicCard> playerBGraveyard;
    //private List<BasicCard>[] playerGraveyards;
    private SecureRandom sRandom = new SecureRandom();

    private Server() {
        maxTableSize = 5;
        round = 1;
        players = new Player[2];
        playerAGraveyard = new ArrayList<>();
        playerBGraveyard = new ArrayList<>();
        playerADeck = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 0, 1, 2),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not exist", 0, 2, 1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 0, 5, 1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 0, 1, 1),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 0, 2, 2),
                new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 0, 3, 3),
                new BasicCreatureCard(7, "Daim", "Chocolate with hard filling", "does not exist yet", 0, 3, 5),
                new BasicCreatureCard(8, "Bounty", "Chocolate with coconut filling", "does not exist yet", 0, 2, 1),
                new BasicCreatureCard(9, "Hubba Bubba", "Sweet chewing-gum", "does not exist yet", 0, 3, 3),
                new BasicCreatureCard(10, "Raisin", "Dried up grapes pretending to be candy", "does not exist yet",0, 1, 1))
        );

        playerBDeck = new ArrayList<>(Arrays.asList(
                new BasicCreatureCard(1, "Marshmallow", "White soft treat", "does not exist yet", 0, 1, 2),
                new BasicCreatureCard(2, "Plopp","Chocolate with gooey caramel center", "does not excist", 0, 2, 1),
                new BasicCreatureCard(3, "Smash", "Crispy chocolate treat", "does not exist yet", 0, 5, 1),
                new BasicCreatureCard(4, "Crazy face", "Sour chewy candy", "does not exist yet", 0, 1, 1),
                new BasicCreatureCard(5, "Djungelvrål", "Licorice candy that makes you scream", "does not exist yet", 0, 2, 2),
                new BasicCreatureCard(6, "Nick's", "Sugar-free candy", "does not exist yet", 0, 3, 3),
                new BasicCreatureCard(7, "Daim", "Chocolate with hard filling", "does not exist yet", 0, 3, 5),
                new BasicCreatureCard(8, "Bounty", "Chocolate with coconut filling", "does not exist yet", 0, 2, 1),
                new BasicCreatureCard(9, "Hubba Bubba", "Sweet chewing-gum", "does not exist yet", 0, 3, 3),
                new BasicCreatureCard(10, "Raisin", "Dried up grapes pretending to be candy", "does not exist yet",0, 1, 1))
        );
        Game.getInstance().setPlayerADeck(playerADeck.size());
        Game.getInstance().setPlayerBDeck(playerBDeck.size());

    }
    
    public static Server getInstance() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
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

    //public List<BasicCard>[] getPlayerDecks() { return playerDecks; }

    //public void setPlayerDecks(List<BasicCard> playerDecks) { this.playerDecks = playerDecks; }

    public List<BasicCard> getPlayerAGraveyard() { return playerAGraveyard; }

    public void setPlayerAGraveyard(List<BasicCard> playerAGraveyard) { this.playerAGraveyard = playerAGraveyard; }

    public List<BasicCard> getPlayerBGraveyard() { return playerBGraveyard; }

    //public void setPlayerBGraveyard(List<BasicCard> playerBGraveyard) { this.playerBGraveyard = playerBGraveyard; }

    //public List<BasicCard>[] getPlayerGraveyards() { return playerGraveyards; }

    //public void setPlayerGraveyards(List<BasicCard> playerGraveyards) { this.playerGraveyards = playerGraveyards; }
    //endregion

    // server.Server functions
    public void receiveCommand(String input) {
        if(input.startsWith("ATTACK")) {
            if(input.substring(12).startsWith("ENEMY_CREATURE")) {
                String attack =input.substring(7, 8);
                String defend = input.substring(27);
                command = attackEnemyCreature(Integer.parseInt(input.substring(7, 8)), Integer.parseInt(input.substring(27)));
            } else if (input.substring(12).startsWith("ENEMY_PLAYER")) {
                command = attackEnemyPlayer();
            }

        } else if (input.startsWith("PLACE_CARD")) {
            command = placeCard(Integer.parseInt(input.substring(11)));
        }
        else if (input.startsWith("END_TURN")) {
            endTurn();

        } else if (input.startsWith("QUIT_GAME")) {
            quitGame();
        }
    }

    public int rollDice(int min, int max) {
        return sRandom.nextInt(max - min + 1)+ min;
    }

    public int[] dealCards(int playerTurn) {
        int ids[] = new int[5];

        if(playerTurn == PLAYER_A){

            for (int i = 0; i < 5; i++) {
                ids[i] = dealCard(playerTurn);
            }
            return ids;

        }else{
            for (int i = 0; i < 5; i++) {
                ids[i] = dealCard(playerTurn);
            }
            return ids;
        }


    }

    public int dealCard(int playerTurn) {

        int id;
        if (playerTurn == PLAYER_A) {
            id = playerADeck.get(playerADeck.size() - 1).id;
            playerADeck.remove(playerADeck.size() - 1);
            Game.getInstance().setPlayerADeck(playerADeck.size());
            return id;
        } else {
            id = playerBDeck.get(playerBDeck.size() - 1).id;
            playerBDeck.remove(playerBDeck.size() - 1);
            Game.getInstance().setPlayerBDeck(playerBDeck.size());
            return id;
        }
    }

    public String sendCard(String s) {
        return "";
    }
    
    public String placeCard(int index) {
        if (turn == 0 && playerATableCards.size() != maxTableSize) {
            BasicCard card = players[0].getHand().get(index);
            players[0].getHand().remove(index);
            playerATableCards.add(card);
            randomizeCreatureHp(index);
            Game.getInstance().getPlayerATableCards().add(card);
            return "SUCCESS";
        } else if (turn == 1 && playerBTableCards.size() != maxTableSize){
            BasicCard card = players[1].getHand().get(index);
            players[1].getHand().remove(index);
            playerBTableCards.add(card);
            randomizeCreatureHp(index);
            Game.getInstance().getPlayerBTableCards().add(card);
            return "SUCCESS";
        }
        return "FAIL";
    }

    public String attackEnemyPlayer() {
        String alive = "ALIVE";
        String dead = "DEAD";
        if(turn == 0 ){
            if(playerBTableCards.size() == 0){
                int health = rollDice(1,6);
                players[1].decrementHealth(health);

                //Should we return a string, example: SUCCESS PLAYER ALIVE/SUCCESS PLAYER DEAD?
            }
            
            if(checkPlayerAlive(players[1])) {
                return alive;
            } else {
                System.out.printf("Player %s died, %s won!\n", players[PLAYER_B].getName(), players[PLAYER_A].getName());
                return dead;
            }
        }
        else {
            if(playerATableCards.size() == 0) {
                int health = rollDice(1,6);
                players[0].decrementHealth(health);
            }
    
            if(checkPlayerAlive(players[0])) {
                return alive;
            } else {
                System.out.printf("Player %s died, %s won!\n", players[PLAYER_A].getName(), players[PLAYER_B].getName());
                return dead;
            }
        }
    }

    public String attackEnemyCreature(int attackingCreatureIndex, int defendingCreatureIndex) {
        int playerARoll;
        int playerBRoll;
        int dmg;

        String attackMsg = "SUCCESS A ";    //A = Alive
        String successMsg ="SUCCESS D ";    //D = Dead
        String success = "SUCCESS";
        String fail = "FAIL";


        do{
            playerARoll = Server.getInstance().rollDice(1, 6);
            playerBRoll = Server.getInstance().rollDice(1, 6);
        } while(playerARoll == playerBRoll);

        if (turn == 0) {
            // Player A turn
            if (playerARoll > playerBRoll) {
                dmg = playerARoll - playerBRoll;
                System.out.println(dmg);
                ((BasicCreatureCard)playerBTableCards.get(defendingCreatureIndex)).setHealth((((BasicCreatureCard)playerBTableCards.get(defendingCreatureIndex)).getHealth() - dmg));
                return checkCreatureAlive(defendingCreatureIndex, 1) ? attackMsg + success : successMsg + success;
            } else {
                dmg = playerBRoll - playerARoll;
                System.out.println(dmg);
                ((BasicCreatureCard)playerATableCards.get(attackingCreatureIndex)).setHealth((((BasicCreatureCard)playerATableCards.get(attackingCreatureIndex)).getHealth() - dmg));
                return checkCreatureAlive(attackingCreatureIndex, 0) ? attackMsg + fail : successMsg + fail;
            }
        } else {
            // Player B turn
            if (playerARoll > playerBRoll) {
                dmg = playerARoll - playerBRoll;
                ((BasicCreatureCard)playerBTableCards.get(attackingCreatureIndex)).setHealth((((BasicCreatureCard)playerBTableCards.get(attackingCreatureIndex)).getHealth() - dmg));
                return checkCreatureAlive(attackingCreatureIndex, 1) ? attackMsg + fail : successMsg + fail;
            } else {
                dmg = playerBRoll - playerARoll;
                ((BasicCreatureCard)playerATableCards.get(defendingCreatureIndex)).setHealth((((BasicCreatureCard)playerATableCards.get(defendingCreatureIndex)).getHealth() - dmg));
                return checkCreatureAlive(defendingCreatureIndex, 0) ? attackMsg + success : successMsg + success;
            }
        }
    }


    public void randomizeCreatureHp(int index) {
        if (getTurn() == 0 && playerATableCards.size() > 0) {
                ((BasicCreatureCard)playerATableCards.get(playerATableCards.size() - 1)).setHealth(rollDice(1,10));

        } else if (getTurn() == 1 && playerBTableCards.size() > 0) {
                ((BasicCreatureCard)playerBTableCards.get(playerBTableCards.size() - 1)).setHealth(rollDice(1,10));

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

    public boolean checkCreatureAlive(int index, int player) {
        // Player A turn
        // WRITE TEST FOR THIS FIRST
        if (player == 0) {
            if (((BasicCreatureCard) playerATableCards.get(index)).getHealth() > 0) {
                return true;
            } else {
                moveToGraveyard(index, player);
                return false;
            }
            // Player B turn
        } else {
            if (((BasicCreatureCard) playerBTableCards.get(index)).getHealth() > 0) {
                return true;
            } else {
                moveToGraveyard(index, player);
                return false;
            }
        }
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
        if (turn == 0) {
            turn = 1;
        }
        else {
            turn = 0;
            round++;
        }
    }

    public void quitGame() {
        System.exit(0);
    }

}
