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
    private boolean isDeckPopulated = false;
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
        //int secondaryCheck = 12;
        int attackStart = 7;
        //int attackEnd = 8;
        int defendCheck = 27;
        int cardIndex = 23;
        int attackCreature = 25;
        int attackCreatureEnd = 26;
        if (input.startsWith("ATTACK")) {
            if (input.substring(attackStart).startsWith("P" + board.getTurn() + " ENEMY_CREATURE")) {
                command = attackEnemyCreature(Integer.parseInt(input.substring(attackCreature, attackCreatureEnd)), Integer.parseInt(input.substring(defendCheck)));
            } else if (input.substring(attackStart).startsWith("P" + board.getTurn() + " ENEMY_PLAYER")) {
                command = attackEnemyPlayer(Integer.parseInt(input.substring(cardIndex)));
            }
        } else if (input.startsWith("PLACE P" + board.getTurn())) {
            command = placeCard(Integer.parseInt(input.substring(18)));
        } else if (input.startsWith("END_TURN")) {
            command = endTurn();
        } else if (input.startsWith("QUIT_GAME")) {
            command = quitGame();
        } else if (input.startsWith("LOGIN")) {
            command = login(input);
        } else if(input.startsWith("STARTED")) {
            command = "";
            ++playersCon;
        } else if(input.startsWith("START_CARDS") && playersCon == 2) {
            if(!isDeckPopulated) {
                populateDecks();
                isDeckPopulated = true;
            }
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

    public String login(String input) {
        String email = input.substring(6);
        String name = retrievePlayerName(email);
        int id = retrievePlayerId(email);
        if(board.getPlayers()[0] == null) {
            int pos = 0;
            board.addPlayer(new Player(id, name), pos);
            return String.valueOf("LOGIN " + id + " " + name + " " + pos);
        } else {
            int pos = 1;
            board.addPlayer(new Player(id, name), pos);
            String command = String.valueOf("LOGIN " + id + " " + name + " " + pos);

            if(!started) {
                command += " START";
                started = true;
            }
            return command;
        }
    }

    public void populateDecks() {
        int deckId;
        if (board.getPlayers()[0].getId() == 8) {
            deckId = 1;
        } else {
            deckId = 2;
        }
        List<Integer> player0 = queryHandler.fetchDeckCreatureCardId(board.getPlayers()[0].getId(), deckId);

        if (board.getPlayers()[1].getId() == 9) {
            deckId = 2;
        } else {
            deckId = 1;
        }
        List<Integer> player1 = queryHandler.fetchDeckCreatureCardId(board.getPlayers()[1].getId(), deckId);

        for (int id : player0) {
            board.getPlayers()[0].getDeck().add(queryHandler.fetchCreatureCardId(id));
        }

        for (int id : player1) {
            board.getPlayers()[1].getDeck().add(queryHandler.fetchCreatureCardId(id));
        }
        
        shuffleDeck(board.getPlayers()[0].getDeck());
        shuffleDeck(board.getPlayers()[1].getDeck());
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


    public String placeCard(int index) {
        var player = board.getPlayers()[board.getTurn()];
        if (player.getTable().size() != board.maxTableSize) {
            BasicCard card = player.getHand().get(index);

            if (player.getMana() >= card.getManaCost()) {

                player.decreaseMana(card.getManaCost());
                player.getHand().remove(index);
                player.getTable().add(card);
                player.getTable().get(player.getTable().size() - 1).setIsConsumed(true);

                return "PLACE P" + board.getTurn() + "_CREATURE " + index;
            } else {
                return "PLACE P" + board.getTurn() + "_FAILED NO_MANA";
            }
        } /*else if (board.getTurn() == board.PLAYER_B && board.getPlayerBTableCards().size() != board.maxTableSize) {

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
        }*/
        return "PLACE P" + board.getTurn() + "_FAILED NO_ROOM";
    }


    //TODO Check if the attacking creature exists with same as enemy craeture method
    private String attackEnemyPlayer(int index) {
        var player = board.getPlayers()[board.getTurn()];
        var enemyPlayer = board.getPlayers()[board.checkTurnCombat()];
        //if (board.getTurn() == board.PLAYER_A) {
            if (enemyPlayer.getTable().size() == 0) {
                if (!player.getTable().get(index).getIsConsumed()) {
                    player.getTable().get(index).setIsConsumed(true);
                    enemyPlayer.decrementHealth(((BasicCreatureCard) player.getTable().get(index)).getAttack());
                    //Should we return a string, example: SUCCESS PLAYER ALIVE/SUCCESS PLAYER DEAD?
                    if (checkPlayerAlive(enemyPlayer)) {
                        return "P" + board.checkTurnCombat() + "_PLAYER " + enemyPlayer.getHealth();
                    } else {
                        System.out.printf("Player %s died, %s won!\n", enemyPlayer.getName(), player.getName());
                        return "P" + board.checkTurnCombat() + "_PLAYER DEAD";
                    }
                } else {
                    return "P" + board.getTurn() + " CREATURE " + index + " IS_CONSUMED";
                }
            }

            return "P" + board.checkTurnCombat() + "_PLAYER FAIL CARDS_ON_TABLE";

        /*}*/ /*else {
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
        }*/


    }

    public String attackEnemyCreature(int attackingCreatureIndex, int defendingCreatureIndex) {
        String returnString = "ATTACK_RESULT";
        BasicCreatureCard player = null;
        if(board.getPlayers()[board.getTurn()].getTable().size() == attackingCreatureIndex + 1) {
            player = ((BasicCreatureCard) board.getPlayers()[board.getTurn()].getTable().get(attackingCreatureIndex));
        } else {
            return returnString + " WRONG_CREATURE";
        }
    
        if (board.getPlayers()[board.checkTurnCombat()].getTable().size() != 0) {
            var enemyPlayer = ((BasicCreatureCard) board.getPlayers()[board.checkTurnCombat()].getTable().get(defendingCreatureIndex));
            if (!player.getIsConsumed()) {
    
                player.decrementHealth(enemyPlayer.getDefense());
                enemyPlayer.decrementHealth(player.getAttack());
    
                player.setIsConsumed(true);
    
                boolean attackingCreature = checkCreatureAlive(attackingCreatureIndex, board.getTurn());
                boolean defendingCreature = checkCreatureAlive(defendingCreatureIndex, board.checkTurnCombat());
    
                returnString += " P" + board.getTurn() + "_TABLE " + attackingCreatureIndex + " HP " + player.getHealth();
                returnString += " | P" + board.checkTurnCombat() + "_TABLE " + defendingCreatureIndex + " HP " + enemyPlayer.getHealth();
    
                return returnString;
            } else {
                return returnString + " FAILED " + attackingCreatureIndex + " IS_CONSUMED";
            }
        } else {
            return returnString + " FAILED NO_ENEMY_CREATURES";
        }
    


        /*if (board.getTurn() == board.PLAYER_A) {
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
        }*/
    }

/*
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
*/
    public boolean checkPlayerAlive(Player p) {
        return p.getHealth() > 0;
    }

    public boolean checkCreatureAlive(int index, int player) {
        // Player A turn
        // WRITE TEST FOR THIS FIRST
        //if (player == board.PLAYER_A) {
            if (((BasicCreatureCard) board.getPlayers()[player].getTable().get(index)).getHealth() > 0) {
                return true;
            } else {
                moveToGraveyard(index, player);
                return false;
            }

            // Player B turn
        /*} else {
            if (((BasicCreatureCard) board.getPlayerBTableCards().get(index)).getHealth() > 0) {
                return true;
            } else {
                moveToGraveyard(index, player);
                return false;
            }
        }*/
    }

    private void moveToGraveyard(int index, int player) {
        //if (player == board.PLAYER_A) {
            BasicCard card = board.getPlayers()[player].getTable().get(index);

            board.getPlayers()[player].getGraveyard().add(card);
            board.getPlayers()[player].getTable().remove(index);

          /*  game.getPlayerATableCards().remove(index);
            game.incrementPlayerAGraveyard();*/

        /*} else {
            BasicCard card = board.getPlayerBTableCards().get(index);
            board.getPlayerBGraveyard().add(card);
            board.getPlayerBTableCards().remove(index);
            game.getPlayerBTableCards().remove(index);
            game.incrementPlayerBGraveyard();


        }*/
        //System.out.println(playerBTableCards.get(index));
    }


    public String endTurn() {

        //if (board.getTurn() == board.PLAYER_A) {
            for (int i = 0; i < board.getPlayers()[board.getTurn()].getTable().size(); i++) {
                board.getPlayers()[board.getTurn()].getTable().get(i).setIsConsumed(false);
            }
            board.increaseTurn(1);
            board.getPlayers()[board.getTurn()].setMana(board.getRound());

            return "ROUND " + board.getRound() + " TURN " + board.getTurn();

        /*} else {
            for (int i = 0; i < board.getPlayerBTableCards().size(); i++) {
                board.getPlayerBTableCards().get(i).setIsConsumed(false);
            }
            board.setTurn(board.PLAYER_A);

            board.setRound();
        }*/
    }


    public void shuffleDeck(List<BasicCard> deck) {
        Collections.shuffle(deck);

    }

    public String quitGame() {
        return "QUIT_GAME P" + board.getTurn();
    }

}
