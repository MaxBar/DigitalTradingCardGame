package main;

import Game.Game;
import card.BasicCreatureCard;
import player.Player;
import server.Server;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    private static int choice;
    private static Scanner sc = new Scanner(System.in);
    private static Server server = Server.getInstance();
    private static Game game = Game.getInstance();
    private static Player[] players = {new Player(1,"Johan",1), new Player(2,"Linn",1)};
    
    private static int playerA = server.PLAYER_A;
    private static int playerB = server.PLAYER_B;
    
    public static void main(String[] args) {



        //region initialize players and set hand

        server.setPlayers(players);

        game.setPlayerA(server.getPlayers()[playerA]);
        game.setPlayerB(server.getPlayers()[playerB]);

        server.getPlayers()[playerA].receiveStartCards(server.dealCards(server.PLAYER_A));
        server.getPlayers()[playerB].receiveStartCards(server.dealCards(server.PLAYER_B));
        players[playerA].receiveCard(server.dealCard(server.getTurn()));
        //endregion
        printMenu();
    }

    public static void printMenu() {

        int quitMessage = 9;
        do {
            printBoard();
            System.out.println("---------- MENU ----------");
            if(server.getTurn() == playerA && server.getPlayers()[playerA].getHand().size() > 0
                    || server.getTurn() == playerB && server.getPlayers()[playerB].getHand().size() > 0 ) {
                System.out.println("1) Place card");
            }
            if(server.getRound() > 1 && server.getTurn() == playerA && server.getPlayerATableCards().size() > 0
                    || server.getRound() > 1 && server.getTurn() == playerB && server.getPlayerBTableCards().size() > 0) {
                System.out.println("2) Attack");
            }
            System.out.println("3) End turn");
            System.out.println("9) Quit game");
            System.out.println("---------- **** ----------");
            choice = sc.nextInt();
            checkChoice(choice);
        } while(choice != quitMessage);
    }


    public static void checkChoice(int choice) {
        switch (choice) {
            case 1:
                if(server.getTurn() == playerA && server.getPlayers()[playerA].getHand().size() > 0
                        || server.getTurn() == playerB && server.getPlayers()[playerB].getHand().size() > 0 ) {
                    printCaseOne();
                }
                break;
            case 2:
                if(server.getTurn() == playerA && server.getPlayerATableCards().size() > 0
                        || server.getTurn() == playerB && server.getPlayerBTableCards().size() > 0 ) {
                    printCaseTwo();
                }
                break;
            case 3:
                printCaseThree();
                break;
            case 9:
                printCaseNine();
                break;
            default:
                break;
        }
    }

    private static void printCaseOne() {

//        String message;
//        String cardName;
        System.out.println("---------- PLACE CARD ----------");
        if (server.getTurn() == playerA) {
            placeCard(playerA);
//            for (int i = 0; i < players[playerA].getHand().size() ; i++) {
//                System.out.println((i + 1) + ") " + players[playerA].getHand().get(i).getName());
//
//            }
//            choice = sc.nextInt() - 1;
//            cardName = players[playerA].getHand().get(choice).getName();
//            players[playerA].placeCard(choice);
//            message = server.getCommand();
        } else {
            placeCard(playerB);
//            for (int i = 0; i < players[playerB].getHand().size() ; i++) {
//                System.out.println((i + 1) + ") " + players[playerB].getHand().get(i).getName());
//            }
//            choice = sc.nextInt() - 1;
//            cardName = players[playerB].getHand().get(choice).getName();
//            players[playerB].placeCard(choice);
//            message = server.getCommand();

        }
//        System.out.println("---------- *********** ----------");
//        //TODO Place card in next available spot
//
//        if (message.equals("SUCCESS")) {
//            System.out.println("You placed card: " + cardName);
//        } else {
//            System.out.println("There is no more room to place your card");
//        }
    }
    
    private static void placeCard(int player) {
        String cardName;
        String message;
        for(int i = 0; i < players[player].getHand().size(); ++i) {
            System.out.println((i + 1) + ") " + players[player].getHand().get(i).getName());
        }
        choice = sc.nextInt() - 1;
        cardName = players[player].getHand().get(choice).getName();
        players[player].placeCard(choice);
        message = server.getCommand();
    
        System.out.println("---------- *********** ----------");
    
        if (message.equals("SUCCESS")) {
            System.out.println("You placed card: " + cardName);
        } else {
            System.out.println("There is no more room to place your card");
        }
    }

    private static void printCaseTwo() {
        String cardName;
        System.out.println("---------- ATTACK WITH CARD ----------");
        if (server.getTurn() == playerA && server.getRound() > 1) {
            for (int i = 0; i < server.getPlayerATableCards().size(); i++) {
                if(server.getPlayerATableCards().get(i).getIsConsumed() == false) {
                    System.out.println((i + 1) + ") " + server.getPlayerATableCards().get(i).getName() + " HP: " + ((BasicCreatureCard) server.getPlayerATableCards().get(i)).getHealth());
                }

            }
            System.out.println("9) BACK");
            choice = sc.nextInt() - 1;
            if(choice !=8) {
                cardName = server.getPlayerATableCards().get(choice).getName();
                if (!server.getPlayerATableCards().get(choice).getIsConsumed()) {
                    printAttackDestination(choice, cardName);
                    server.getPlayerATableCards().get(choice).setIsConsumed(true);
                }
            }
            if(choice == 8) {
              printMenu();
            }


        } else if (server.getTurn() == playerB && server.getRound() > 1) {
            for (int i = 0; i < server.getPlayerBTableCards().size(); i++) {
                if(server.getPlayerBTableCards().get(i).getIsConsumed() == false) {
                    System.out.println((i + 1) + ") " + server.getPlayerBTableCards().get(i).getName() + " HP: " + ((BasicCreatureCard) server.getPlayerBTableCards().get(i)).getHealth());
                }
            }
            System.out.println("9) BACK");
            choice = sc.nextInt() - 1;
            if(choice == 8) {
                printMenu();
            }
            if(choice !=8) {
                cardName = server.getPlayerBTableCards().get(choice).getName();
                if (!server.getPlayerBTableCards().get(choice).getIsConsumed()) {
                    printAttackDestination(choice, cardName);
                    server.getPlayerBTableCards().get(choice).setIsConsumed(true);
                }
            }
        }
        System.out.println("---------- *********** ----------");

    }
    
    private static void printAttackDestination(int choice, String cardName) {
        if(server.getTurn() == playerA) {
            if(server.getPlayerBTableCards().size() > 0) {
                printEnemyCards(choice, cardName);
            } else if(server.getPlayerBTableCards().size() == 0 && server.getRound() > 1) {
                printAttackPlayer(choice, cardName);
            }
        } else {
            if(server.getPlayerATableCards().size() > 0) {
                printEnemyCards(choice, cardName);
            } else if(server.getPlayerATableCards().size() == 0 && server.getRound() > 1) {
                printAttackPlayer(choice, cardName);
            }
        }
    }
    
    private static void printAttackPlayer(int choice, String cardName) {
        int attackPlayer = 0;
        System.out.println("------------- ATTACK PLAYER ------------");
        if(server.getTurn() == playerA) {
            System.out.printf("1) %s \n", players[playerB].getName());
            attackPlayer = sc.nextInt();
            System.out.printf("Player %s attacked with %s on player %s\n", players[playerA].getName(), cardName, players[playerB].getName());
            players[playerA].attackPlayer(choice);
        } else {
            System.out.printf("1) %s \n", players[playerA].getName());
            attackPlayer = sc.nextInt();
            System.out.printf("Player %s attacked with %s on player %s\n", players[playerB].getName(), cardName, players[playerA].getName());
            players[playerB].attackPlayer(choice);
        }
        
        if(server.getCommand().equals("DEAD")) {
            players[playerA].quitGame();
        }
    }

    private static void printCaseThree() {
        System.out.println("---------- END TURN ----------");
        System.out.println("TURN " + server.getTurn());
        System.out.println("ROUND " +server.getRound());
        if(server.getTurn() == playerA && server.getPlayerADeck().size() == 0){
            System.out.println("************GAME OVER***********");
            server.quitGame();
        }else if(server.getTurn() == playerB && server.getPlayerBDeck().size() == 0){
            System.out.println("************GAME OVER***********");
            server.quitGame();
        }
        server.endTurn();
        if(server.getTurn() == 0) {
            players[playerA].receiveCard(server.dealCard(server.getTurn()));
        } else {
            players[playerB].receiveCard(server.dealCard(server.getTurn()));
        }
        System.out.println("TURN " +server.getTurn());
        System.out.println("ROUND " +server.getRound());

    }

    private static void printCaseNine() {
        System.out.println("---------- QUIT GAME ----------");
    }

    private static void printEnemyCards(int choice, String cardName) {
        String enemyCardName;
        int enemyChoice;
        System.out.println("---------- ATTACK ENEMY CARD ----------");
        if (server.getTurn() == playerA) {
            for (int i = 0; i < server.getPlayerBTableCards().size(); i++) {
                System.out.println((i + 1) + ") " + server.getPlayerBTableCards().get(i).getName() + " HP: " + ((BasicCreatureCard)server.getPlayerBTableCards().get(i)).getHealth());
            }
            enemyChoice = sc.nextInt() - 1;
            enemyCardName = server.getPlayerBTableCards().get(choice).getName();
            players[playerA].attackCreature(choice, enemyChoice);
            printAttackResults(cardName, enemyCardName);

        } else {
            for (int i = 0; i < server.getPlayerATableCards().size(); i++) {
                System.out.println((i + 1) + ") " + server.getPlayerATableCards().get(i).getName() + " HP: " + ((BasicCreatureCard)server.getPlayerATableCards().get(i)).getHealth());
            }
            enemyChoice = sc.nextInt() - 1;
            enemyCardName = server.getPlayerATableCards().get(choice).getName();
            players[playerB].attackCreature(choice, enemyChoice);
            printAttackResults(cardName, enemyCardName);

        }

        System.out.println("---------- *********** ----------");
    }

    public static void printAttackResults(String attackingCardName, String defendingCardName) {
        int aliveStart = 8;
        int aliveEnd = 9;
        int successOrFail = 10;
        if(server.getTurn() == playerA){
            if (server.getCommand().substring(aliveStart, aliveEnd).startsWith("A")) {
                if(server.getCommand().substring(successOrFail).startsWith("SUCCESS")){
                    System.out.println("Player A successfully attacked Enemy card " + defendingCardName + " with card " + attackingCardName + ", but " + defendingCardName + " is still alive");
                } else if (server.getCommand().substring(successOrFail).startsWith("FAIL")){
                    System.out.println("Player A lost against Enemy card " + defendingCardName + " with card " + attackingCardName + ", but " + attackingCardName + " is still alive");
                }

            } else if (server.getCommand().substring(aliveStart, aliveEnd).startsWith("D")) {
                if(server.getCommand().substring(successOrFail).startsWith("SUCCESS")){
                    System.out.println("Player A successfully attacked Enemy card " + defendingCardName + " with card " + attackingCardName + " and " + defendingCardName + " died");
                } else if (server.getCommand().substring(successOrFail).startsWith("FAIL")){
                    System.out.println("Player A lost against Enemy card " + defendingCardName + " with card " + attackingCardName + " and " + attackingCardName + " died");

                }

            }
        } else {
            if (server.getCommand().substring(aliveStart, aliveEnd).startsWith("A")) {
                if(server.getCommand().substring(successOrFail).startsWith("SUCCESS")){
                    System.out.println("Player B successfully attacked Enemy card " + defendingCardName + " with card " + attackingCardName + ", but " + defendingCardName + " is still alive");
                } else if (server.getCommand().substring(successOrFail).startsWith("FAIL")){
                    System.out.println("Player B lost against Enemy card " + defendingCardName + " with card " + attackingCardName + ", but " + attackingCardName + " is still alive");
                }

            } else if (server.getCommand().substring(aliveStart, aliveEnd).startsWith("D")) {
                if(server.getCommand().substring(successOrFail).startsWith("SUCCESS")){
                    System.out.println("Player B successfully attacked Enemy card " + defendingCardName + " with card " + attackingCardName + " and " + defendingCardName + " died");

                } else if (server.getCommand().substring(successOrFail).startsWith("FAIL")){
                    System.out.println("Player B lost against Enemy card " + defendingCardName + " with card " + attackingCardName + " and " + attackingCardName + " died");

                }

            }
        }

    }
    
    private static void printBoard() {
        System.out.println("**************************************");
        System.out.printf("%s - HP: %s \t\t| %s - HP: %s\n",
                players[playerA].getName(), players[playerA].getHealth(),
                players[playerB].getName(), players[playerB].getHealth());
        System.out.println("--------------------------------------");
        for(int i = 0; i < game.getPlayerATableCards().size(); ++i) {
            String finalString = "";
            if(game.getPlayerATableCards().size() - 1 >= i) {
                finalString = String.format("%-12s HP: %s \t| ", game.getPlayerATableCards().get(i).getName(), ((BasicCreatureCard)game.getPlayerATableCards().get(i)).getHealth());
            } else {
                finalString = "EMPTY \t\t\t\t| ";
            }
            if(game.getPlayerBTableCards().size() - 1 >= i) {
                finalString += String.format("%-12s HP: %s", game.getPlayerBTableCards().get(i).getName(), ((BasicCreatureCard)game.getPlayerBTableCards().get(i)).getHealth());
            } else {
                finalString += "EMPTY";
            }
            System.out.println(finalString);
        }
        System.out.println("--------------------------------------");
        String playerA = game.getPlayerAGraveyard() > 1 ? "cards" : "card";
        String playerB = game.getPlayerBGraveyard() > 1 ? "cards" : "card";
        System.out.println(String.format("Graveyard: %s %s \t| Graveyard: %s %s", game.getPlayerAGraveyard(), playerA, game.getPlayerBGraveyard(), playerB));
        playerA = game.getPlayerADeck() > 1 ? "cards" : "card";
        playerB = game.getPlayerBDeck() > 1 ? "cards" : "card";
        System.out.println(String.format("Deck: %s %s \t\t| Deck: %s %s", game.getPlayerADeck(), playerA, game.getPlayerBDeck(), playerB));
        playerA = game.getPlayerA().getHand().size() > 1 ? "cards" : "card";
        playerB = game.getPlayerB().getHand().size() > 1 ? "cards" : "card";
        System.out.println(String.format("Hand: %s %s \t\t| Hand: %s %s", game.getPlayerA().getHand().size(), playerA, game.getPlayerB().getHand().size(), playerB));
        System.out.println("**************************************");
    }
}
