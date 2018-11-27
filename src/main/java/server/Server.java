package server;

import board.Board;
import card.*;
import player.Player;
import repository.QueryHandler;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Server {

    // Fields
    private static Server server = null;
    private SecureRandom sRandom = new SecureRandom();
    private QueryHandler queryHandler = new QueryHandler();
    private boolean started = false;
    private boolean isDeckPopulated = false;
    private int playersCon;
    private Board board = new Board();
    private NetworkServer network = NetworkServer.getInstance();

    private Server() {
        playersCon = 0;
    }

    public static Server getInstance() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }
    
    void receiveCommand(String input) throws IOException {
        int attackStart = 7;
        int cardIndex = 23;
        int attackCreature = 25;
        int attackCreatureEnd = 26;
        if (input.startsWith("ATTACK")) {
            if (input.substring(attackStart).startsWith("P" + board.getTurn() + " ENEMY_CREATURE")) {
               attackEnemyCreature(Integer.parseInt(input.substring(attackCreature, attackCreatureEnd)), Integer.parseInt(input.substring(27)));// board.getPlayers()[board.checkTurnCombat()].getTable().size() - 1);//, Integer.parseInt(input.substring(defendCheck)));
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
        } else if (input.startsWith("P" + board.getTurn() + "_DRAW")) {
            System.out.println("I Draw");
            dealTurnCard();
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

    private void login(String input) {
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

    private void populateDecks() {
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


    private void dealCards(int playerTurn) throws IOException {
        int handSize = 5;
        String ids[] = new String[handSize];

        if (playerTurn == board.getTurn()) {
            for (int i = 0; i < handSize; i++) {
                ids[i] = dealCard();
            }
            StringBuilder id = new StringBuilder();
            for (String id1 : ids) {
                id.append(id1).append(", ");
            }
            id.setLength(id.length() - 2);
            try {
                network.sendMsgToClient(String.format("P%s DEALT_CARDS %s", playerTurn, id.toString()), network.getClientIP().get(playerTurn));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < handSize; i++) {
                ids[i] = dealCard();
            }
        }


    }

    private String dealCard() throws IOException {
        String id;
        List<BasicCard> deck = board.getPlayers()[board.getTurn()].getDeck();
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
            network.sendMsgToClient("ENEMY_HAND INCREMENT", network.getClientIP().get(board.checkTurnCombat()));
            network.sendMsgToClient("ENEMY_DECK DECREMENT", network.getClientIP().get(board.checkTurnCombat()));
            network.sendMsgToClient("PLAYER_DECK DECREMENT", network.getClientIP().get(board.getTurn()));
        }
        deck.remove(deck.size() - 1);
        return id;
    }
    
    private String dealTurnCard() throws IOException {
        String id;
        List<BasicCard> deck = board.getPlayers()[board.getTurn()].getDeck();
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
            network.sendMsgToClient("ENEMY_HAND INCREMENT", network.getClientIP().get(board.checkTurnCombat()));
            network.sendMsgToClient("ENEMY_DECK DECREMENT", network.getClientIP().get(board.checkTurnCombat()));
            network.sendMsgToClient("PLAYER_DECK DECREMENT", network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("DRAW_CARD %s", id), network.getClientIP().get(board.getTurn()));
        }
        deck.remove(deck.size() - 1);
        return id;
    }


    private void placeCard(int index) throws IOException {
        Player player = board.getPlayers()[board.getTurn()];
        if (player.getTable().size() <= board.maxTableSize) {
            BasicCard card = player.getHand().get(index);

            if (player.getMana() >= card.getManaCost()) {
                try {
                    network.sendMsgToClient(String.format("P%s PLACE_CREATURE_SUCCESS %s MP %s", board.getTurn(), index, player.getMana()),network.getClientIP().get(board.getTurn()));
                    network.sendMsgToClient(String.format("P%s PLACE_CREATURE_SUCCESS %s MP %s", board.getTurn(), card.getId(), player.getMana()),network.getClientIP().get(board.checkTurnCombat()));
                    network.sendMsgToClient("ENEMY_HAND DECREMENT", network.getClientIP().get(board.checkTurnCombat()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.decreaseMana(card.getManaCost());
                player.getTable().add(card);
                if(player.getTable().get(player.getTable().size() - 1) instanceof SpecialAbilityCreatureCard &&
                        ((SpecialAbilityCreatureCard)player.getTable().get(player.getTable().size() - 1)).getKeyword() == EKeyword.CHARGE) {
                    player.getTable().get(player.getTable().size() - 1).setIsConsumed(false);
                    network.sendMsgToClient(String.format("P%s CONSUMED_FALSE %s", board.getTurn(), player.getTable().size() - 1), network.getClientIP().get(board.getTurn()));
                } else if(player.getTable().get(player.getTable().size() - 1) instanceof  SpecialAbilityCreatureCard &&
                        ((SpecialAbilityCreatureCard)player.getTable().get(player.getTable().size() - 1)).getKeyword() == EKeyword.COOLDOWN) {
                    ((SpecialAbilityCreatureCard) player.getTable().get(player.getTable().size() - 1)).decrementAbilityValue();
                }
                player.getHand().remove(index);
            } else {
                network.sendMsgToClient(String.format("P%s PLACE_CREATURE_FAILURE %s NO_MANA", board.getTurn(), index), network.getClientIP().get(board.getTurn()));
                network.sendMsgToClient(String.format("P%s PLACE_CREATURE_FAILURE %s NO_MANA", board.getTurn(), index), network.getClientIP().get(board.checkTurnCombat()));
            }
        } else {
            network.sendMsgToClient(String.format("P%s PLACE_CREATURE_FAILURE %s NO_ROOM", board.getTurn(), index), network.getClientIP().get(board.getTurn()));
        }
    }


    //TODO Check if the attacking creature exists with same as enemy craeture method
    private void attackEnemyPlayer(int index) throws IOException {
        Player playerTurn = board.getPlayers()[board.getTurn()];
        Player enemyPlayerTurn = board.getPlayers()[board.checkTurnCombat()];
    
        BasicCreatureCard player = null;
        if(playerTurn.getTable().size() > index) {
            player = ((BasicCreatureCard) playerTurn.getTable().get(index));
        } else {
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_OUT_OF_BOUNDS", playerTurn), network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_OUT_OF_BOUNDS", playerTurn), network.getClientIP().get(board.checkTurnCombat()));
        }

            if (enemyPlayerTurn.getTable().size() == 0) {
                assert player != null;
                if (playerTurn.getTable().get(index) != null && !player.getIsConsumed()) {
                    player.setIsConsumed(true);
                    enemyPlayerTurn.decrementHealth(player.getAttack());

                    if (checkPlayerAlive(enemyPlayerTurn)) {
                        network.sendMsgToClient(String.format("P%s ATTACK_RESULT_SUCCESS P%s HP %s", board.getTurn(), board.checkTurnCombat(), enemyPlayerTurn.getHealth()), network.getClientIP().get(board.getTurn()));
                        network.sendMsgToClient(String.format("P%s ATTACK_RESULT_SUCCESS P%s HP %s", board.getTurn(), board.checkTurnCombat(), enemyPlayerTurn.getHealth()), network.getClientIP().get(board.checkTurnCombat()));
                    } else {
                        System.out.printf("Player %s died, %s won!\n", enemyPlayerTurn.getName(), playerTurn.getName());
                        queryHandler.saveWinner(playerTurn.getId());
                        queryHandler.saveLoser(enemyPlayerTurn.getId());
                        network.sendMsgToClient(String.format("P%s ATTACK_RESULT_SUCCESS P%s_DEAD", board.getTurn(), board.checkTurnCombat()), network.getClientIP().get(board.getTurn()));
                        network.sendMsgToClient(String.format("P%s ATTACK_RESULT_SUCCESS P%s_DEAD", board.getTurn(), board.checkTurnCombat()), network.getClientIP().get(board.checkTurnCombat()));
                    }
                    network.sendMsgToClient(String.format("P%s CONSUMED_TRUE_ %s", board.getTurn(), index), network.getClientIP().get(board.getTurn()));
                } else {
                    network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_%s_IS_CONSUMED", board.getTurn(), index),  network.getClientIP().get(board.getTurn()));
                }
            } else {
                network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CARDS_ON_TABLE", board.checkTurnCombat()), network.getClientIP().get(board.getTurn()));
                network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CARDS_ON_TABLE", board.checkTurnCombat()), network.getClientIP().get(board.checkTurnCombat()));
            }
    }

    private void attackEnemyCreature(int attackingCreatureIndex, int defendingCreatureIndex) throws IOException {
        BasicCreatureCard playerCreature = null;

        if(board.getPlayers()[board.getTurn()].getTable().size() >= (attackingCreatureIndex + 1)) {
            playerCreature = ((BasicCreatureCard) board.getPlayers()[board.getTurn()].getTable().get(attackingCreatureIndex));
        } else {
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_OUT_OF_BOUNDS", board.getTurn()), network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_OUT_OF_BOUNDS", board.getTurn()), network.getClientIP().get(board.checkTurnCombat()));
        }
    
        if (board.getPlayers()[board.checkTurnCombat()].getTable().size() != 0) {
            BasicCreatureCard enemyPlayerCreature = ((BasicCreatureCard) board.getPlayers()[board.checkTurnCombat()].getTable().get(defendingCreatureIndex));
            assert playerCreature != null;
            if (!playerCreature.getIsConsumed()) {
    
                playerCreature.decrementHealth(enemyPlayerCreature.getDefense());
                enemyPlayerCreature.decrementHealth(playerCreature.getAttack());
    
                playerCreature.setIsConsumed(true);

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
    
                checkCreatureAlive(attackingCreatureIndex, board.getTurn());
                checkCreatureAlive(defendingCreatureIndex, board.checkTurnCombat());
            } else {
                network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_%s_IS_CONSUMED",
                        board.getTurn(),
                        attackingCreatureIndex), network.getClientIP().get(board.getTurn()));
                network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE CREATURE_%s_IS_CONSUMED",
                        board.getTurn(),
                        attackingCreatureIndex), network.getClientIP().get(board.checkTurnCombat()));
            }
        } else {
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE NO_ENEMY_CARDS_ON_TABLE", board.getTurn()),network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("P%s ATTACK_RESULT_FAILURE NO_ENEMY_CARDS_ON_TABLE", board.getTurn()),network.getClientIP().get(board.checkTurnCombat()));
        }
    }
    
    private void useEnemyCreature(int index) throws IOException {
        Player player = board.getPlayers()[board.getTurn()];
        Player enemyPlayer = board.getPlayers()[board.checkTurnCombat()];
        if ((enemyPlayer.getTable().size()) > 0) {
            BasicMagicCard card = (BasicMagicCard)player.getHand().get(index);
            if (card.getKeyword() == EKeyword.DIRECTATTACK) {
                if (player.getMana() >= card.getManaCost()) {
                    player.decreaseMana(card.getManaCost());
                    network.sendMsgToClient("ENEMY_HAND DECREMENT", network.getClientIP().get(board.checkTurnCombat()));
                    network.sendMsgToClient(String.format("P%s GRAVEYARD INCREMENT", board.getTurn()), network.getClientIP().get(board.getTurn()));
                    network.sendMsgToClient(String.format("P%s GRAVEYARD INCREMENT", board.getTurn()), network.getClientIP().get(board.checkTurnCombat()));
                    network.sendMsgToClient(String.format("P%s USE_MAGIC_CREATURE_SUCCESS %s", board.getTurn(), index), network.getClientIP().get(board.getTurn()));
                    network.sendMsgToClient(String.format("ENEMY_MANA %s", player.getMana()), network.getClientIP().get(board.checkTurnCombat()));
                    int lastIndex = (enemyPlayer.getTable().size() - 1);
                    int dmg = ((BasicMagicCard) player.getHand().get(index)).getAbilityValue();
                    ((BasicCreatureCard) enemyPlayer.getTable().get(lastIndex)).decrementHealth(dmg);
                    network.sendMsgToClient(String.format("P%s CREATURE_HP %s %s", board.checkTurnCombat(), lastIndex, ((BasicCreatureCard) enemyPlayer.getTable().get(lastIndex)).getHealth()), network.getClientIP().get(board.getTurn()));
                    network.sendMsgToClient(String.format("P%s CREATURE_HP %s %s", board.checkTurnCombat(), lastIndex, ((BasicCreatureCard) enemyPlayer.getTable().get(lastIndex)).getHealth()), network.getClientIP().get(board.checkTurnCombat()));
                    player.getHand().remove(index);
                    checkCreatureAlive(lastIndex, board.checkTurnCombat());
                } else {
                    network.sendMsgToClient(String.format("USE_MAGIC_CREATURE_FAILURE %s NO_MANA", index), network.getClientIP().get(board.getTurn()));
                }
            }
        } else {
            network.sendMsgToClient(String.format("USE_MAGIC_CREATURE_FAILURE %s NO_ENEMY_CREATURES", index), network.getClientIP().get(board.getTurn()));
        }
    }

    private boolean checkPlayerAlive(Player p) {
        return p.getHealth() > 0;
    }

    private void checkCreatureAlive(int index, int player) throws IOException {
        if (((BasicCreatureCard) board.getPlayers()[player].getTable().get(index)).getHealth() <= 0) {
            moveToGraveyard(index, player);
        }
    }

    private void moveToGraveyard(int index, int player) throws IOException {
            BasicCard card = board.getPlayers()[player].getTable().get(index);

            board.getPlayers()[player].getGraveyard().add(card);
            board.getPlayers()[player].getTable().remove(index);
            network.sendMsgToClient(String.format("P%s REMOVE_FROM_TABLE %s", player, index), network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("P%s REMOVE_FROM_TABLE %s", player, index), network.getClientIP().get(board.checkTurnCombat()));
            network.sendMsgToClient(String.format("P%s GRAVEYARD INCREMENT", player), network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("P%s GRAVEYARD INCREMENT", player), network.getClientIP().get(board.checkTurnCombat()));
    }

    private void endTurn() {
        for(BasicCard card : board.getPlayers()[board.getTurn()].getTable()) {
            if (card instanceof SpecialAbilityCreatureCard && ((SpecialAbilityCreatureCard) card).getKeyword() == EKeyword.COOLDOWN && ((SpecialAbilityCreatureCard) card).getAbilityValue() > 0) {
                ((SpecialAbilityCreatureCard) card).decrementAbilityValue();
            } else if (card instanceof SpecialAbilityCreatureCard && ((SpecialAbilityCreatureCard) card).getKeyword() == EKeyword.COOLDOWN && ((SpecialAbilityCreatureCard) card).getAbilityValue() == 0) {
                card.setIsConsumed(false);
            } else {
                card.setIsConsumed(false);
            }
        }
            board.increaseTurn(1);
            board.getPlayers()[board.getTurn()].setMana(board.getRound());
        try {
            network.sendMsgToClient(String.format("ROUND %s TURN %s", board.getRound(), board.getTurn()), network.getClientIP().get(board.getTurn()));
            network.sendMsgToClient(String.format("ROUND %s TURN %s", board.getRound(), board.getTurn()), network.getClientIP().get(board.checkTurnCombat()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Player playerTurn = board.getPlayers()[board.getTurn()];
        Player enemyPlayerTurn = board.getPlayers()[board.checkTurnCombat()];

        if(board.getPlayers()[board.getTurn()].getHand().size() == 0 && board.getPlayers()[board.getTurn()].getDeck().size() == 0) {
            System.out.printf("Player %s lost, no cards left, %s won!\n", playerTurn.getName(), enemyPlayerTurn.getName());
            queryHandler.saveLoser(playerTurn.getId());
            queryHandler.saveWinner(enemyPlayerTurn.getId());
            quitGame();
        }
    }

    private void shuffleDeck(List<BasicCard> deck) {
        Collections.shuffle(deck);

    }

    private void quitGame() {
        System.out.printf("P%s QUIT_GAME", board.getTurn());
    }

}
