package server;

import board.Board;
import card.BasicCard;
import card.BasicCreatureCard;
import player.Player;
import repository.QueryHandler;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Server {

    // Fields
    private static Server server = null;
    
    private String command;
    private SecureRandom sRandom = new SecureRandom();
    private QueryHandler queryHandler = new QueryHandler();
    private boolean started = false;
    private int playersCon;
    //private Game game = Game.getInstance();
    public Board board = new Board();

    private Server() {
        playersCon = 0;
        //game.setPlayerADeck(board.getPlayerADeck().size());
        //game.setPlayerBDeck(board.getPlayerBDeck().size());
        //shuffleDeck(board.getPlayerADeck());
        //shuffleDeck(board.getPlayerBDeck());
        this.board = board;
    }

    public static Server getInstance() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    //endregion

    // server.Server functions1
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    
    public void receiveCommand(String input) {
        int secondaryCheck = 12;
        int attackStart = 7;
        int attackEnd = 8;
        int defendCheck = 27;
        /*if (input.startsWith("ATTACK")) {
            if (input.substring(secondaryCheck).startsWith("ENEMY_CREATURE")) {
                command = attackEnemyCreature(Integer.parseInt(input.substring(attackStart, attackEnd)), Integer.parseInt(input.substring(defendCheck)));
            } else if (input.substring(secondaryCheck).startsWith("ENEMY_PLAYER")) {
                command = attackEnemyPlayer();
            }

        } else if (input.startsWith("PLACE_CARD")) {
            command = placeCard(Integer.parseInt(input.substring(11)));
        } else if (input.startsWith("END_TURN")) {
            endTurn();

        } else */if (input.startsWith("QUIT_GAME")) {
            quitGame();
        } else if (input.startsWith("LOGIN")) {
            String email = input.substring(6);
            String name = retrievePlayerName(email);
            int id = retrievePlayerId(email);
            if(board.getPlayers()[0] == null) {
                int pos = 0;
                board.addPlayer(new Player(id, name), pos);
                command = String.valueOf("LOGIN " + id + " " + name + " " + pos);
            } else {
                int pos = 1;
                board.addPlayer(new Player(id, name), pos);
                command = String.valueOf("LOGIN " + id + " " + name + " " + pos);
                
                if(!started) {
                    command += " START";
                    started = true;
                }
            }
        } else if(input.startsWith("STARTED")) {
            ++playersCon;
        } else if(input.startsWith("START_CARDS") && playersCon == 2) {
            command = dealCards(board.getTurn());
            board.increaseTurn(1);
        } else if (input.startsWith("P" + board.getTurn() + "_DRAW")) {
            command = "DEALT CARD P" + board.getTurn() + " " + Integer.toString(dealCard(board.getTurn()));
        }
    }

    private int retrievePlayerId(String email) {
        return queryHandler.fetchPlayerId(email);
    }
    
    private String retrievePlayerName(String email) {
        return queryHandler.fetchPlayerName(email);
    }

    public int rollDice(int min, int max) {
        return sRandom.nextInt(max - min + 1) + min;
    }

    public String dealCards(int playerTurn) {
        int handSize = 5;
        int ids[] = new int[handSize];

        if (playerTurn == board.getTurn()) {

            for (int i = 0; i < handSize; i++) {
                ids[i] = dealCard(playerTurn);
            }
            StringBuilder id = new StringBuilder();
            System.out.println(String.valueOf(ids));
            //String[] temp = Arrays.toString(ids).split("[\\[\\]]")[1].split(", ");
            for (int id1 : ids) {
                id.append(id1).append(", ");
            }
            id.setLength(id.length() - 2);
            return "DEALT CARDS P" + playerTurn + " " + id.toString();

        } else {
            for (int i = 0; i < handSize; i++) {
                ids[i] = dealCard(playerTurn);
            }
            return "";
            //System.out.println(Arrays.toString(ids).split("[\\[\\]]")[1].split(", "));
            //return Arrays.toString(ids).split("[\\[\\]]")[1].split(", ");
        }


    }

    public int dealCard(int playerTurn) {
        int id;
        var deck = board.getPlayers()[board.getTurn()].getDeck();
        id = deck.get(deck.size() - 1).id;
        board.getPlayers()[board.getTurn()].getHand().add(deck.get(deck.size() - 1));
        deck.remove(deck.size() - 1);
        return id;
    }

    /*public String sendCard(String s) {
        return "";
    }

    public String placeCard(int index) {
        if (board.getTurn() == board.PLAYER_A && board.getPlayerATableCards().size() != board.maxTableSize) {
            BasicCard card = board.getPlayers()[board.PLAYER_A].getHand().get(index);

            if (board.getPlayers()[board.PLAYER_A].getMana() >= card.getManaCost()) {

                board.getPlayers()[board.PLAYER_A].decreaseMana(card.getManaCost());
                board.getPlayers()[board.PLAYER_A].getHand().remove(index);
                board.getPlayerATableCards().add(card);
                randomizeCreatureHp(index);
                game.getPlayerATableCards().add(card);

                return "SUCCESS";
            } else {
                return "";
            }
        } else if (board.getTurn() == board.PLAYER_B && board.getPlayerBTableCards().size() != board.maxTableSize) {

            BasicCard card = board.getPlayers()[board.PLAYER_B].getHand().get(index);

            if (board.getPlayers()[board.PLAYER_B].getMana() >= card.getManaCost()) {

                board.getPlayers()[board.PLAYER_B].decreaseMana(card.getManaCost());
                board.getPlayers()[board.PLAYER_B].getHand().remove(index);
                board.getPlayerBTableCards().add(card);
                randomizeCreatureHp(index);
                game.getPlayerBTableCards().add(card);

                return "SUCCESS";
            } else {
                return "";
            }
        }
        return "FAIL";
    }



    public String attackEnemyPlayer() {
        String alive = "ALIVE";
        String dead = "DEAD";
        if (board.getTurn() == board.PLAYER_A) {
            if (board.getPlayerBTableCards().size() == 0) {
                int health = rollDice(1, 6);
                board.getPlayers()[board.PLAYER_B].decrementHealth(health);

                //Should we return a string, example: SUCCESS PLAYER ALIVE/SUCCESS PLAYER DEAD?
            }

            if (checkPlayerAlive(board.getPlayers()[board.PLAYER_B])) {
                return alive;
            } else {
                System.out.printf("Player %s died, %s won!\n", board.getPlayers()[board.PLAYER_B].getName(), board.getPlayers()[board.PLAYER_A].getName());
                return dead;
            }
        } else {
            if (board.getPlayerATableCards().size() == 0) {
                int health = rollDice(1, 6);
                board.getPlayers()[board.PLAYER_A].decrementHealth(health);
            }

            if (checkPlayerAlive(board.getPlayers()[board.PLAYER_A])) {
                return alive;
            } else {
                System.out.printf("Player %s died, %s won!\n", board.getPlayers()[board.PLAYER_A].getName(), board.getPlayers()[board.PLAYER_B].getName());
                return dead;
            }
        }
    }

    public String attackEnemyCreature(int attackingCreatureIndex, int defendingCreatureIndex) {
        int playerARoll;
        int playerBRoll;
        int dmg;

        String attackMsg = "SUCCESS A ";    //A = Alive
        String successMsg = "SUCCESS D ";    //D = Dead
        String success = "SUCCESS";
        String fail = "FAIL";


        do {
            playerARoll = rollDice(1, 6);
            playerBRoll = rollDice(1, 6);
        } while (playerARoll == playerBRoll);

        if (board.getTurn() == board.PLAYER_A) {
            // Player A turn
            if (playerARoll > playerBRoll) {
                dmg = playerARoll - playerBRoll;
                System.out.println(dmg);
                ((BasicCreatureCard) board.getPlayerBTableCards().get(defendingCreatureIndex)).setHealth((((BasicCreatureCard) board.getPlayerBTableCards().get(defendingCreatureIndex)).getHealth() - dmg));
                return checkCreatureAlive(defendingCreatureIndex, board.PLAYER_B) ? attackMsg + success : successMsg + success;
            } else {
                dmg = playerBRoll - playerARoll;
                System.out.println(dmg);
                ((BasicCreatureCard) board.getPlayerATableCards().get(attackingCreatureIndex)).setHealth((((BasicCreatureCard) board.getPlayerATableCards().get(attackingCreatureIndex)).getHealth() - dmg));
                return checkCreatureAlive(attackingCreatureIndex, board.PLAYER_A) ? attackMsg + fail : successMsg + fail;
            }
        } else {
            // Player B turn
            if (playerARoll > playerBRoll) {
                dmg = playerARoll - playerBRoll;
                ((BasicCreatureCard) board.getPlayerBTableCards().get(attackingCreatureIndex)).setHealth((((BasicCreatureCard) board.getPlayerBTableCards().get(attackingCreatureIndex)).getHealth() - dmg));
                return checkCreatureAlive(attackingCreatureIndex, board.PLAYER_B) ? attackMsg + fail : successMsg + fail;
            } else {
                dmg = playerBRoll - playerARoll;
                ((BasicCreatureCard) board.getPlayerATableCards().get(defendingCreatureIndex)).setHealth((((BasicCreatureCard) board.getPlayerATableCards().get(defendingCreatureIndex)).getHealth() - dmg));
                return checkCreatureAlive(defendingCreatureIndex, board.PLAYER_A) ? attackMsg + success : successMsg + success;
            }
        }
    }


    public void randomizeCreatureHp(int index) {
        if (board.getTurn() == board.PLAYER_A && board.getPlayerATableCards().size() > 0) {
            ((BasicCreatureCard) board.getPlayerATableCards().get(board.getPlayerATableCards().size() - 1)).setHealth(rollDice(1, 10));

        } else if (board.getTurn() == board.PLAYER_A && board.getPlayerBTableCards().size() > 0) {
            ((BasicCreatureCard) board.getPlayerBTableCards().get(board.getPlayerBTableCards().size() - 1)).setHealth(rollDice(1, 10));

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
        if (player == board.PLAYER_A) {
            if (((BasicCreatureCard) board.getPlayerATableCards().get(index)).getHealth() > 0) {
                return true;
            } else {
                moveToGraveyard(index, player);
                return false;
            }
            // Player B turn
        } else {
            if (((BasicCreatureCard) board.getPlayerBTableCards().get(index)).getHealth() > 0) {
                return true;
            } else {
                moveToGraveyard(index, player);
                return false;
            }
        }
    }

    public void moveToGraveyard(int index, int player) {
        if (player == board.PLAYER_A) {
            BasicCard card = board.getPlayerATableCards().get(index);

            board.getPlayerAGraveyard().add(card);
            board.getPlayerATableCards().remove(index);

            game.getPlayerATableCards().remove(index);
            game.incrementPlayerAGraveyard();

        } else {
            BasicCard card = board.getPlayerBTableCards().get(index);
            board.getPlayerBGraveyard().add(card);
            board.getPlayerBTableCards().remove(index);
            game.getPlayerBTableCards().remove(index);
            game.incrementPlayerBGraveyard();


        }
        //System.out.println(playerBTableCards.get(index));
    }

    public void endTurn() {

        if (board.getTurn() == board.PLAYER_A) {
            for (int i = 0; i < board.getPlayerATableCards().size(); i++) {
                board.getPlayerATableCards().get(i).setIsConsumed(false);
            }
            board.setTurn(board.PLAYER_B);

        } else {
            for (int i = 0; i < board.getPlayerBTableCards().size(); i++) {
                board.getPlayerBTableCards().get(i).setIsConsumed(false);
            }
            board.setTurn(board.PLAYER_A);

            board.setRound();
        }
    }*/


    public void shuffleDeck(List<BasicCard> deck) {
        Collections.shuffle(deck);

    }

    public void quitGame() {
        System.exit(0);
    }

}
