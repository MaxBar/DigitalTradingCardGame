package main;

import Game.Game;
import card.BasicCreatureCard;
import player.Player;
import server.Server;

import java.util.Scanner;

public class Main {
    private static int choice;
    private static Scanner sc = new Scanner(System.in);
    private static Server server = Server.getInstance();
    private static Game game = Game.getInstance();
    private static Player[] players = {new Player(1,"Johan",1), new Player(2,"Linn",1)};
    public static void main(String[] args) {



        //region initialize players and set hand

        server.setPlayers(players);

        game.setPlayerA(server.getPlayers()[0]);
        game.setPlayerB(server.getPlayers()[1]);

        server.getPlayers()[0].receiveStartCards(server.dealCards());
        server.getPlayers()[1].receiveStartCards(server.dealCards());
        //endregion
        printMenu();
    }

    public static void printMenu() {

        int quitMessage = 9;
        do {
            System.out.println("---------- MENU ----------");
            if(server.getTurn() == server.PLAYER_A && server.getPlayers()[server.PLAYER_A].getHand().size() > 0
                    || server.getTurn() == server.PLAYER_B && server.getPlayers()[server.PLAYER_B].getHand().size() > 0 ) {
                System.out.println("1) Place card");
            }
            if(server.getTurn() == server.PLAYER_A && server.getPlayerATableCards().size() > 0
                    || server.getTurn() == server.PLAYER_B && server.getPlayerBTableCards().size() > 0 ) {
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
                if(server.getTurn() == server.PLAYER_A && server.getPlayers()[server.PLAYER_A].getHand().size() > 0
                        || server.getTurn() == server.PLAYER_B && server.getPlayers()[server.PLAYER_B].getHand().size() > 0 ) {
                    printCaseOne();
                }
                break;
            case 2:
                if(server.getTurn() == server.PLAYER_A && server.getPlayerATableCards().size() > 0
                        || server.getTurn() == server.PLAYER_B && server.getPlayerBTableCards().size() > 0 ) {
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

        String message;
        String cardName;
        System.out.println("---------- PLACE CARD ----------");
        if (server.getTurn() == server.PLAYER_A) {
            for (int i = 0; i < players[server.PLAYER_A].getHand().size() ; i++) {
                System.out.println((i + 1) + ") " + players[server.PLAYER_A].getHand().get(i).getName());

            }
            choice = sc.nextInt() - 1;
            cardName = players[server.PLAYER_A].getHand().get(choice).getName();
            players[server.PLAYER_A].placeCard(choice);
            message = server.getCommand();
        } else {
            for (int i = 0; i < players[server.PLAYER_B].getHand().size() ; i++) {
                System.out.println((i + 1) + ") " + players[server.PLAYER_B].getHand().get(i).getName());
            }
            choice = sc.nextInt() - 1;
            cardName = players[server.PLAYER_B].getHand().get(choice).getName();
            players[server.PLAYER_B].placeCard(choice);
            message = server.getCommand();

        }
        System.out.println("---------- *********** ----------");
        //TODO Place card in next available spot

        if (message == "SUCCESS") {
            System.out.println("You placed card: " + cardName);
        } else {
            System.out.println("There is no more room to place your card");
        }
    }

    private static void printCaseTwo() {
        String cardName;
        System.out.println("---------- ATTACK WITH CARD ----------");
        if (server.getTurn() == server.PLAYER_A) {
            for (int i = 0; i < server.getPlayerATableCards().size(); i++) {
                System.out.println((i + 1) + ") " + server.getPlayerATableCards().get(i).getName() + " HP: " + ((BasicCreatureCard)server.getPlayerATableCards().get(i)).getHealth());
            }
            choice = sc.nextInt() - 1;
            cardName = server.getPlayerATableCards().get(choice).getName();
            printEnemyCards(choice, cardName);

        } else {
            for (int i = 0; i < server.getPlayerBTableCards().size(); i++) {
                System.out.println((i + 1) + ") " + server.getPlayerBTableCards().get(i).getName() + " HP: " + ((BasicCreatureCard)server.getPlayerBTableCards().get(i)).getHealth());
            }
            choice = sc.nextInt() - 1;
            cardName = server.getPlayerBTableCards().get(choice).getName();
            printEnemyCards(choice, cardName);

        }
        System.out.println("---------- *********** ----------");

    }

    private static void printCaseThree() {
        System.out.println("---------- END TURN ----------");
        System.out.println("TURN " + server.getTurn());
        System.out.println("ROUND " +server.getRound());
        server.endTurn();
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
        if (server.getTurn() == server.PLAYER_A) {
            for (int i = 0; i < server.getPlayerBTableCards().size(); i++) {
                System.out.println((i + 1) + ") " + server.getPlayerBTableCards().get(i).getName() + " HP: " + ((BasicCreatureCard)server.getPlayerBTableCards().get(i)).getHealth());
            }
            enemyChoice = sc.nextInt() - 1;
            enemyCardName = server.getPlayerBTableCards().get(choice).getName();
            players[server.PLAYER_A].attackCreature(choice, enemyChoice);
            printAttackResults(cardName, enemyCardName);

        } else {
            for (int i = 0; i < server.getPlayerATableCards().size(); i++) {
                System.out.println((i + 1) + ") " + server.getPlayerATableCards().get(i).getName() + " HP: " + ((BasicCreatureCard)server.getPlayerATableCards().get(i)).getHealth());
            }
            enemyChoice = sc.nextInt() - 1;
            enemyCardName = server.getPlayerATableCards().get(choice).getName();
            players[server.PLAYER_B].attackCreature(choice, enemyChoice);
            printAttackResults(cardName, enemyCardName);

        }

        System.out.println("---------- *********** ----------");
    }

    public static void printAttackResults(String attackingCardName, String defendingCardName) {
        if(server.getTurn() == server.PLAYER_A){
            if (server.getCommand().substring(8, 9).startsWith("A")) {
                if(server.getCommand().substring(10).startsWith("SUCCESS")){
                    System.out.println("Player A successfully attacked Enemy card " + defendingCardName + " with card " + attackingCardName + ", but " + defendingCardName + " is still alive");
                } else if (server.getCommand().substring(10).startsWith("FAIL")){
                    System.out.println("Player A lost against Enemy card " + defendingCardName + " with card " + attackingCardName + ", but " + attackingCardName + " is still alive");
                }

            } else if (server.getCommand().substring(8, 9).startsWith("D")) {
                if(server.getCommand().substring(10).startsWith("SUCCESS")){
                    System.out.println("Player A successfully attacked Enemy card " + defendingCardName + " with card " + attackingCardName + " and " + defendingCardName + " died");
                } else if (server.getCommand().substring(10).startsWith("FAIL")){
                    System.out.println("Player A lost against Enemy card " + defendingCardName + " with card " + attackingCardName + " and " + attackingCardName + " died");

                }

            }
        } else {
            if (server.getCommand().substring(8, 9).startsWith("A")) {
                if(server.getCommand().substring(10).startsWith("SUCCESS")){
                    System.out.println("Player B successfully attacked Enemy card " + defendingCardName + " with card " + attackingCardName + ", but " + defendingCardName + " is still alive");
                } else if (server.getCommand().substring(10).startsWith("FAIL")){
                    System.out.println("Player B lost against Enemy card " + defendingCardName + " with card " + attackingCardName + ", but " + attackingCardName + " is still alive");
                }

            } else if (server.getCommand().substring(8, 9).startsWith("D")) {
                if(server.getCommand().substring(10).startsWith("SUCCESS")){
                    System.out.println("Player B successfully attacked Enemy card " + defendingCardName + " with card " + attackingCardName + " and " + defendingCardName + " died");

                } else if (server.getCommand().substring(10).startsWith("FAIL")){
                    System.out.println("Player B lost against Enemy card " + defendingCardName + " with card " + attackingCardName + " and " + attackingCardName + " died");

                }

            }
        }

    }
}
