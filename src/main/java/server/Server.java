package server;

import board.Board;
import card.*;
import player.Player;
import repository.QueryHandler;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Server {

    // Fields
    private static Server server = null;
    
    //private String command;
    private SecureRandom sRandom = new SecureRandom();
    private QueryHandler queryHandler = new QueryHandler();
    private boolean started = false;
    private boolean isDeckPopulated = false;
    private int playersCon;
    //private Game game = Game.getInstance();
    public Board board = new Board();
    NetworkServer network = NetworkServer.getInstance();

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
    /*public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }*/
    
    public void receiveCommand(String input) throws IOException {
        //int secondaryCheck = 12;
        int attackStart = 7;
        //int attackEnd = 8;
        int defendCheck = 27;
        int cardIndex = 23;
        int attackCreature = 25;
        int attackCreatureEnd = 26;
        if (input.startsWith("ATTACK")) {
            if (input.substring(attackStart).startsWith("P" + board.getTurn() + " ENEMY_CREATURE")) {
               attackEnemyCreature(Integer.parseInt(input.substring(attackCreature, attackCreatureEnd)), board.getPlayers()[board.checkTurnCombat()].getTable().size() - 1);//, Integer.parseInt(input.substring(defendCheck)));
            } else if (input.substring(attackStart).startsWith("P" + board.getTurn() + " ENEMY_PLAYER")) {
                attackEnemyPlayer(Integer.parseInt(input.substring(cardIndex)));
            }
        } else if (input.startsWith("PLACE P" + board.getTurn())) {
            placeCard(Integer.parseInt(input.substring(18, 19)));
        } else if(input.startsWith("USE P" + board.getTurn() + " MAGIC_CREATURE")){
            useEnemyCreature((Integer.parseInt(input.substring(input.length()-1))));
        } else if (input.startsWith("END_TURN")) {
            endTurn();
        } else if (input.startsWith("QUIT_GAME")) {
            quitGame();
        } else if (input.startsWith("LOGIN")) {
            login(input);
        } else if(input.startsWith("STARTED")) {
            try {
                NetworkServer.getInstance().sendMsgToClient("STARTED",NetworkServer.getInstance().getClientIP().get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                NetworkServer.getInstance().sendMsgToClient("STARTED",NetworkServer.getInstance().getClientIP().get(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ++playersCon;
        } else if(input.startsWith("START_CARDS") && playersCon == 2) {
            if(!isDeckPopulated) {
                populateDecks();
                isDeckPopulated = true;
            }
            dealCards(board.getTurn());
            //board.increaseTurn(1);
        } else if (input.startsWith("P" + board.getTurn() + "_DRAW")) {
            System.out.println("I Draw");
            dealTurnCard();
            //network.sendMsgToClient(String.format("P%s DEALT_CARD %s", board.getTurn(), Integer.toString(dealCard(board.getTurn()))), network.getClientIP().get(board.getTurn()));
            //+ board.getTurn() + " " + Integer.toString(dealCard(board.getTurn()));
        } else {

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

    public void login(String input) {
        String email = input.substring(6);
        String name = retrievePlayerName(email);
        int id = retrievePlayerId(email);
        if(board.getPlayers()[0] == null) {
            int pos = 0;
            board.addPlayer(new Player(id, name), pos);
            try {
                network.sendMsgToClient(String.format("LOGIN %s ID_%s PLAYER_%s NAME_%s", email, id, pos, name), network.getClientIP().get(board.getTurn()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //String.format("LOGIN %s ID_%s PLAYER_%s NAME_%s", email, id, pos, name);
        } else {
            int pos = 1;
            board.addPlayer(new Player(id, name), pos);
            String command = String.format("LOGIN %s ID_%s PLAYER_%s NAME_%s", email, id, pos, name);

            if(!started) {
                command += " START";
                started = true;
            }
            try {
                network.sendMsgToClient(command, NetworkServer.getInstance().getClientIP().get(board.getTurn()));
                network.sendMsgToClient(command, NetworkServer.getInstance().getClientIP().get(board.checkTurnCombat()));
            } catch (IOException e) {
                e.printStackTrace();
            }

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
        List<Integer> player0Magic = queryHandler.fetchDeckMagicCardId(board.getPlayers()[0].getId(), deckId);

        if (board.getPlayers()[1].getId() == 9) {
            deckId = 2;
        } else {
            deckId = 1;
        }
        
        List<Integer> player1 = queryHandler.fetchDeckCreatureCardId(board.getPlayers()[1].getId(), deckId);
        List<Integer> player1Magic = queryHandler.fetchDeckMagicCardId(board.getPlayers()[1].getId(), deckId);

        for (int id : player0) {
            if(queryHandler.fetchCheckCardType(id) == 0) {
                board.getPlayers()[0].getDeck().add(queryHandler.fetchCreatureCardId(id));
            } else if(queryHandler.fetchCheckCardType(id) == 1) {
                board.getPlayers()[0].getDeck().add(queryHandler.fetchSpecialAbilityCreatureCardId(id));
            }
            
        }
        
        for(int id : player0Magic) {
            board.getPlayers()[0].getDeck().add(queryHandler.fetchMagicCardId(id));
        }
        

        for (int id : player1) {
            if(queryHandler.fetchCheckCardType(id) == 0) {
                board.getPlayers()[1].getDeck().add(queryHandler.fetchCreatureCardId(id));
            } else if(queryHandler.fetchCheckCardType(id) == 1) {
                board.getPlayers()[1].getDeck().add(queryHandler.fetchSpecialAbilityCreatureCardId(id));
            }
        }
        
        for(int id : player1Magic) {
            board.getPlayers()[1].getDeck().add(queryHandler.fetchMagicCardId(id));
        }
        
        shuffleDeck(board.getPlayers()[0].getDeck());
        shuffleDeck(board.getPlayers()[1].getDeck());
    }


    public void dealCards(int playerTurn) throws IOException {
        int handSize = 5;
        String ids[] = new String[handSize];

        if (playerTurn == board.getTurn()) {
            for (int i = 0; i < handSize; i++) {
                ids[i] = dealCard();
            }
            StringBuilder id = new StringBuilder();
            System.out.println(String.valueOf(ids));
            //String[] temp = Arrays.toString(ids).split("[\\[\\]]")[1].split(", ");
            for (String id1 : ids) {
                id.append(id1).append(", ");
            }
            id.setLength(id.length() - 2);
            try {
                network.sendMsgToClient(String.format("P%s DEALT_CARDS %s", playerTurn, id.toString()), network.getClientIP().get(playerTurn));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //String.format("P%s DEALT_CARDS %s", playerTurn, id.toString());
            //return "DEALT_CARDS P" + playerTurn + " " + id.toString();
        } else {
            for (int i = 0; i < handSize; i++) {
                ids[i] = dealCard();
            }

            //System.out.println(Arrays.toString(ids).split("[\\[\\]]")[1].split(", "));
            //return Arrays.toString(ids).split("[\\[\\]]")[1].split(", ");
        }


    }

    public String dealCard() throws IOException {
        String id;
        var deck = board.getPlayers()[board.getTurn()].getDeck();
        id = Integer.toString(deck.get(deck.size() - 1).id);
        if(deck.get(deck.size() - 1) instanceof SpecialAbilityCreatureCard) {
            id = "S_" + id;
        } else if(deck.get(deck.size() - 1) instanceof  BasicCreatureCard) {
            id = "B_" + id;
        } else if(deck.get(deck.size() - 1) instanceof  BasicMagicCard) {
            id = "M_" + id;
        }
        board.getPlayers()[board.getTurn()].getHand().add(deck.get(deck.size() - 1));
        if(deck.size() > 0) {
        //if(board.getPlayers()[board.getTurn()].getHand().size() > 0) {
            network.sendMsgToClient(String.format("ENEMY_HAND INCREMENT"), network.getClientIP().get(board.checkTurnCombat()));
            network.sendMsgToClient(String.format("ENEMY_DECK DECREMENT"), network.getClientIP().get(board.checkTurnCombat()));
            network.sendMsgToClient(String.format("PLAYER_DECK DECREMENT"), network.getClientIP().get(board.getTurn()));
        }
        deck.remove(deck.size() - 1);
        return id;
    }
    
    public String dealTurnCard() throws IOException {
        String id;
        var deck = board.getPlayers()[board.getTurn()].getDeck();
        id = Integer.toString(deck.get(deck.size() - 1).id);
        if(deck.get(deck.size() - 1) instanceof SpecialAbilityCreatureCard) {
            id = "S_" + id;
        } else if(deck.get(deck.size() - 1) instanceof  BasicCreatureCard) {
            id = "B_" + id;
        } else if(deck.get(deck.size() - 1) instanceof  BasicMagicCard) {
            id = "M_" + id;
        }
        board.getPlayers()[board.getTurn()].getHand().add(deck.get(deck.size() - 1));
        if(deck.size() > 0) {
        //if(board.getPlayers()[board.getTurn()].getHand().size() > 0) {
            network.sendMsgToClient(String.format("ENEMY_HAND INCREMENT"), network.getClientIP().get(board.checkTurnCombat()));
            network.sendMsgToClient(String.format("ENEMY_DECK DECREMENT"), network.getClientIP().get(board.checkTurnCombat()));
            network.sendMsgToClient(String.format("PLAYER_DECK DECREMENT"), network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("DRAW_CARD %s", id), network.getClientIP().get(board.getTurn()));
        }
        deck.remove(deck.size() - 1);
        return id;
    }


    public void placeCard(int index) throws IOException {
        var player = board.getPlayers()[board.getTurn()];
        if (player.getTable().size() <= board.maxTableSize) {
            BasicCard card = player.getHand().get(index);

            if (player.getMana() >= card.getManaCost()) {
                try {
                    network.sendMsgToClient(String.format("P%s PLACE_CREATURE_SUCCESS %s", board.getTurn(), index),network.getClientIP().get(board.getTurn()));
                    network.sendMsgToClient(String.format("P%s PLACE_CREATURE_SUCCESS %s", board.getTurn(), card.getId()),network.getClientIP().get(board.checkTurnCombat()));
                    network.sendMsgToClient(String.format("ENEMY_HAND DECREMENT"), network.getClientIP().get(board.checkTurnCombat()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.decreaseMana(card.getManaCost());
                player.getTable().add(card);
                if(player.getTable().get(player.getTable().size() - 1) instanceof SpecialAbilityCreatureCard &&
                        ((SpecialAbilityCreatureCard)player.getTable().get(player.getTable().size() - 1)).getKeyword() == EKeyword.CHARGE) {
                    player.getTable().get(player.getTable().size() - 1).setIsConsumed(false);
                    network.sendMsgToClient(String.format("P%s CONSUMED_FALSE %s", board.getTurn(), player.getTable().size() - 1), network.getClientIP().get(board.getTurn()));
                }
                player.getHand().remove(index);

                
                //return "PLACE P" + board.getTurn() + "_CREATURE " + index;
            } else {
                network.sendMsgToClient(String.format("P%s PLACE_CREATURE_FAILURE %s NO_MANA", board.getTurn(), index), network.getClientIP().get(board.getTurn()));
                //network.sendMsgToClient(String.format("P%s PLACE_CREATURE_FAILURE %s NO_MANA", board.getTurn(), index), network.getClientIP().get(board.checkTurnCombat()));
                //return "PLACE P" + board.getTurn() + "_FAILED NO_MANA";
            }
        } else {
            network.sendMsgToClient(String.format("P%s PLACE_CREATURE_FAILURE %s NO_ROOM", board.getTurn(), index), network.getClientIP().get(board.getTurn()));
        }
        //network.sendMsgToClient(String.format("P%s PLACE_CREATURE_FAILURE %s NO_ROOM", board.getTurn(), index), network.getClientIP().get(board.checkTurnCombat()));
        //return "PLACE P" + board.getTurn() + "_FAILED NO_ROOM";
    }


    //TODO Check if the attacking creature exists with same as enemy craeture method
    private void attackEnemyPlayer(int index) throws IOException {
        var playerTurn = board.getPlayers()[board.getTurn()];
        var enemyPlayerTurn = board.getPlayers()[board.checkTurnCombat()];
    
        BasicCreatureCard player = null;
        if(playerTurn.getTable().size() >= (index + 1)) {
            player = ((BasicCreatureCard) playerTurn.getTable().get(index));
        } else {
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_OUT_OF_BOUNDS", playerTurn), network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_OUT_OF_BOUNDS", playerTurn), network.getClientIP().get(board.checkTurnCombat()));
            //return returnString + " WRONG_CREATURE";
        }
        
        //if (board.getTurn() == board.PLAYER_A) {
            if (enemyPlayerTurn.getTable().size() == 0) {
                if (!player.getIsConsumed()) {
                    player.setIsConsumed(true);
                    enemyPlayerTurn.decrementHealth(player.getAttack());
                    //Should we return a string, example: SUCCESS PLAYER ALIVE/SUCCESS PLAYER DEAD?
                    if (checkPlayerAlive(enemyPlayerTurn)) {
                        network.sendMsgToClient(String.format("P%s ATTACK_RESULT_SUCCESS P%s HP %s", board.getTurn(), board.checkTurnCombat(), enemyPlayerTurn.getHealth()), network.getClientIP().get(board.getTurn()));
                        network.sendMsgToClient(String.format("P%s ATTACK_RESULT_SUCCESS P%s HP %s", board.getTurn(), board.checkTurnCombat(), enemyPlayerTurn.getHealth()), network.getClientIP().get(board.checkTurnCombat()));
                        //return "P" + board.checkTurnCombat() + "_PLAYER " + enemyPlayerTurn.getHealth();
                    } else {
                        System.out.printf("Player %s died, %s won!\n", enemyPlayerTurn.getName(), playerTurn.getName());
                        network.sendMsgToClient(String.format("P%s ATTACK_RESULT_SUCCESS P%s_DEAD", board.getTurn(), board.checkTurnCombat()), network.getClientIP().get(board.getTurn()));
                        network.sendMsgToClient(String.format("P%s ATTACK_RESULT_SUCCESS P%s_DEAD", board.getTurn(), board.checkTurnCombat()), network.getClientIP().get(board.checkTurnCombat()));
                        //System.out.printf("Player %s died, %s won!\n", enemyPlayerTurn.getName(), playerTurn.getName());
                        //return "P" + board.checkTurnCombat() + "_PLAYER DEAD";
                    }
                    network.sendMsgToClient(String.format("P%s CONSUMED_TRUE_ %s", board.getTurn(), index), network.getClientIP().get(board.getTurn()));
                } else {
                    network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_%s_IS_CONSUMED", board.getTurn(), index),  network.getClientIP().get(board.getTurn()));
                    network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_%s_IS_CONSUMED", board.getTurn(), index),  network.getClientIP().get(board.checkTurnCombat()));
                    //return "P" + board.getTurn() + " CREATURE " + index + " IS_CONSUMED";
                }
            } else {
                network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CARDS_ON_TABLE", board.checkTurnCombat()), network.getClientIP().get(board.getTurn()));
                network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CARDS_ON_TABLE", board.checkTurnCombat()), network.getClientIP().get(board.checkTurnCombat()));
            }
            //return "P" + board.checkTurnCombat() + "_PLAYER FAIL CARDS_ON_TABLE";
    }

    public void attackEnemyCreature(int attackingCreatureIndex, int defendingCreatureIndex) throws IOException {
        //String returnString = "ATTACK_RESULT";
        BasicCreatureCard playerCreature = null;
        // == NOT <= IN IF CONDITION
        if(board.getPlayers()[board.getTurn()].getTable().size() >= (attackingCreatureIndex + 1)) {
            playerCreature = ((BasicCreatureCard) board.getPlayers()[board.getTurn()].getTable().get(attackingCreatureIndex));
        } else {
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_OUT_OF_BOUNDS", board.getTurn()), network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_OUT_OF_BOUNDS", board.getTurn()), network.getClientIP().get(board.checkTurnCombat()));
            //return returnString + " WRONG_CREATURE";
        }
    
        if (board.getPlayers()[board.checkTurnCombat()].getTable().size() != 0) {
            var enemyPlayerCreature = ((BasicCreatureCard) board.getPlayers()[board.checkTurnCombat()].getTable().get(defendingCreatureIndex));
            if (!playerCreature.getIsConsumed()) {
    
                playerCreature.decrementHealth(enemyPlayerCreature.getDefense());
                enemyPlayerCreature.decrementHealth(playerCreature.getAttack());
    
                playerCreature.setIsConsumed(true);
    
    
                //returnString += " P" + board.getTurn() + "_TABLE " + attackingCreatureIndex + " HP " + player.getHealth();
                //returnString += " | P" + board.checkTurnCombat() + "_TABLE " + defendingCreatureIndex + " HP " + enemyPlayer.getHealth();
                network.sendMsgToClient(String.format("P%s ATTACK_RESULT_SUCCESS CARD_%s HP %s | P%s_CARD_%s HP %s",
                        board.getTurn(),
                        attackingCreatureIndex,
                        playerCreature.getHealth(),
                        board.checkTurnCombat(),
                        defendingCreatureIndex,
                        enemyPlayerCreature.getHealth()), network.getClientIP().get(board.getTurn()));
                network.sendMsgToClient(String.format("P%s ATTACK_RESULT_SUCCESS CARD_%s HP %s | P%s_CARD_%s HP %s",
                        board.getTurn(),
                        attackingCreatureIndex,
                        playerCreature.getHealth(),
                        board.checkTurnCombat(),
                        defendingCreatureIndex,
                        enemyPlayerCreature.getHealth()), network.getClientIP().get(board.checkTurnCombat()));
    
                network.sendMsgToClient(String.format("P%s CONSUMED_TRUE  %s", board.getTurn(), attackingCreatureIndex), network.getClientIP().get(board.getTurn()));
    
                boolean attackingCreature = checkCreatureAlive(attackingCreatureIndex, board.getTurn());
                boolean defendingCreature = checkCreatureAlive(defendingCreatureIndex, board.checkTurnCombat());
                //return returnString;
            } else {
                network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_%s_IS_CONSUMED",
                        board.getTurn(),
                        attackingCreatureIndex), network.getClientIP().get(board.getTurn()));
                network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_%s_IS_CONSUMED",
                        board.getTurn(),
                        attackingCreatureIndex), network.getClientIP().get(board.checkTurnCombat()));
                //return returnString + " FAILED " + attackingCreatureIndex + " IS_CONSUMED";
            }
        } else {

            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE NO_ENEMY_CARDS_ON_TABLE", board.getTurn()),network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE NO_ENEMY_CARDS_ON_TABLE", board.getTurn()),network.getClientIP().get(board.checkTurnCombat()));
            //return returnString + " FAILED NO_ENEMY_CREATURES";
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
    
    private void useEnemyCreature(int index) throws IOException {
        var player = board.getPlayers()[board.getTurn()];
        var enemyPlayer = board.getPlayers()[board.checkTurnCombat()];
        if ((enemyPlayer.getTable().size()) > 0) {
            BasicMagicCard card = (BasicMagicCard)player.getHand().get(index);
            if (card.getKeyword() == EKeyword.DIRECTATTACK) {
                if (player.getMana() >= card.getManaCost()) {
                    player.decreaseMana(card.getManaCost());
                    player.getHand().remove(index);
                    network.sendMsgToClient("ENEMY_HAND DECREMENT", network.getClientIP().get(board.checkTurnCombat()));
                    network.sendMsgToClient(String.format("P%s GRAVEYARD INCREMENT", board.getTurn()), network.getClientIP().get(board.getTurn()));
                    network.sendMsgToClient(String.format("P%s GRAVEYARD INCREMENT", board.getTurn()), network.getClientIP().get(board.checkTurnCombat()));
                    network.sendMsgToClient(String.format("P%s USE_MAGIC_CREATURE_SUCCESS %s", board.getTurn(), index), network.getClientIP().get(board.getTurn()));
                    int lastIndex = (enemyPlayer.getTable().size() - 1);
                    int dmg = ((BasicMagicCard) player.getTable().get(index)).getAbilityValue();
                    ((BasicCreatureCard) enemyPlayer.getTable().get(lastIndex)).decrementHealth(dmg);
                    checkCreatureAlive(lastIndex, board.checkTurnCombat());
                } else {
                    network.sendMsgToClient(String.format("USE_MAGIC_CREATURE_FAILURE %s NO_MANA", index), network.getClientIP().get(board.getTurn()));
                }
            }
        } else {
            network.sendMsgToClient(String.format("USE_MAGIC_CREATURE_FAILURE %s NO_ENEMY_CREATURES", index), network.getClientIP().get(board.getTurn()));
        }
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

    public boolean checkCreatureAlive(int index, int player) throws IOException {
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

    public void moveToGraveyard(int index, int player) throws IOException {
        //if (player == board.PLAYER_A) {
            BasicCard card = board.getPlayers()[player].getTable().get(index);

            board.getPlayers()[player].getGraveyard().add(card);
            board.getPlayers()[player].getTable().remove(index);
            network.sendMsgToClient(String.format("P%s REMOVE_FROM_TABLE %s", player, index), network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("P%s REMOVE_FROM_TABLE %s", player, index), network.getClientIP().get(board.checkTurnCombat()));
            network.sendMsgToClient(String.format("P%s GRAVEYARD INCREMENT", player), network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("P%s GRAVEYARD INCREMENT", player), network.getClientIP().get(board.checkTurnCombat()));

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


    public void endTurn() {

        //if (board.getTurn() == board.PLAYER_A) {
            for (int i = 0; i < board.getPlayers()[board.getTurn()].getTable().size(); i++) {
                board.getPlayers()[board.getTurn()].getTable().get(i).setIsConsumed(false);
            }
            board.increaseTurn(1);
            board.getPlayers()[board.getTurn()].setMana(board.getRound());
        try {
            network.sendMsgToClient(String.format("ROUND %s TURN %s", board.getRound(), board.getTurn()), network.getClientIP().get(board.getTurn()));
            //network.sendMsgToClient(String.format("ROUND %s TURN %s", board.getRound(), board.getTurn()), network.getClientIP().get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        return String.format("P%s QUIT_GAME", board.getTurn());
    }

}
