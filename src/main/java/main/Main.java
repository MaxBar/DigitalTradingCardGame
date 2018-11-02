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

        server.getPlayers()[0].receiveStartCards(server.dealCards(server.PLAYER_A));
        server.getPlayers()[1].receiveStartCards(server.dealCards(server.PLAYER_B));
        players[0].receiveCard(server.dealCard(server.getTurn()));
        //endregion
        printMenu();
    }

    public static void printMenu() {

        int quitMessage = 9;
        do {
            printBoard();
            System.out.println("---------- MENU ----------");
            if(server.getTurn() == server.PLAYER_A && server.getPlayers()[server.PLAYER_A].getHand().size() > 0
                    || server.getTurn() == server.PLAYER_B && server.getPlayers()[server.PLAYER_B].getHand().size() > 0 ) {
                System.out.println("1) Place card");
            }
            if(server.getRound() > 1 && server.getTurn() == server.PLAYER_A && server.getPlayerATableCards().size() > 0
                    || server.getRound() > 1 && server.getTurn() == server.PLAYER_B && server.getPlayerBTableCards().size() > 0) {
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

        if (message.equals("SUCCESS")) {
            System.out.println("You placed card: " + cardName);
        } else {
            System.out.println("There is no more room to place your card");
        }
    }

    private static void printCaseTwo() {
        String cardName;
        System.out.println("---------- ATTACK WITH CARD ----------");
        if (server.getTurn() == server.PLAYER_A && server.getRound() > 1) {
            for (int i = 0; i < server.getPlayerATableCards().size(); i++) {
                System.out.println((i + 1) + ") " + server.getPlayerATableCards().get(i).getName() + " HP: " + ((BasicCreatureCard)server.getPlayerATableCards().get(i)).getHealth());
            }
            choice = sc.nextInt() - 1;
            cardName = server.getPlayerATableCards().get(choice).getName();
            printAttackDestination(choice, cardName);

        } else if (server.getTurn() == server.PLAYER_B && server.getRound() > 1) {
            for (int i = 0; i < server.getPlayerBTableCards().size(); i++) {
                System.out.println((i + 1) + ") " + server.getPlayerBTableCards().get(i).getName() + " HP: " + ((BasicCreatureCard)server.getPlayerBTableCards().get(i)).getHealth());
            }
            choice = sc.nextInt() - 1;
            cardName = server.getPlayerBTableCards().get(choice).getName();
            printAttackDestination(choice, cardName);

        }
        System.out.println("---------- *********** ----------");

    }
    
    private static void printAttackDestination(int choice, String cardName) {
        if(server.getTurn() == server.PLAYER_A) {
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
        if(server.getTurn() == server.PLAYER_A) {
            System.out.printf("1) %s \n", players[server.PLAYER_B].getName());
            attackPlayer = sc.nextInt();
            System.out.printf("Player %s attacked with %s on player %s\n", players[server.PLAYER_A].getName(), cardName, players[server.PLAYER_B].getName());
            players[server.PLAYER_A].attackPlayer(choice);
        } else {
            System.out.printf("1) %s \n", players[server.PLAYER_A].getName());
            attackPlayer = sc.nextInt();
            System.out.printf("Player %s attacked with %s on player %s\n", players[server.PLAYER_B].getName(), cardName, players[server.PLAYER_A].getName());
            players[server.PLAYER_B].attackPlayer(choice);
        }
        
        if(server.getCommand().equals("DEAD")) {
            players[0].quitGame();
        }
    }

    private static void printCaseThree() {
        System.out.println("---------- END TURN ----------");
        System.out.println("TURN " + server.getTurn());
        System.out.println("ROUND " +server.getRound());
        if(server.getTurn() == server.PLAYER_A && server.getPlayerADeck().size() == 0){
            System.out.println("************GAME OVER***********");
            server.quitGame();
        }else if(server.getTurn() == server.PLAYER_B && server.getPlayerBDeck().size() == 0){
            System.out.println("************GAME OVER***********");
            server.quitGame();
        }
        server.endTurn();
        if(server.getTurn() == 0) {
            players[0].receiveCard(server.dealCard(server.getTurn()));
        } else {
            players[1].receiveCard(server.dealCard(server.getTurn()));
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
    
    private static void printBoard() {
        System.out.println("**************************************");
        System.out.println(String.format("%s - HP: %s \t\t| %s - HP: %s",
                players[0].getName(), players[0].getHealth(),
                players[1].getName(), players[1].getHealth()));
        System.out.println("--------------------------------------");
        for(int i = 0; i < 5; ++i) {
            String finalString = "";
            if(game.getPlayerATableCards().size() - 1 >= i) {
                finalString = String.format("%-12s HP: %s \t| ", game.getPlayerATableCards().get(i).getName(), ((BasicCreatureCard)game.getPlayerATableCards().get(i)).getHealth());
            } else {
                finalString = "EMPTY \t\t\t\t| ";
            }
            if(game.getPlayerBTableCards().size() - 1 >= i) {
                finalString += String.format("%-12s, HP: %s", game.getPlayerBTableCards().get(i).getName(), ((BasicCreatureCard)game.getPlayerBTableCards().get(i)).getHealth());
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
        System.out.println(String.format("Deck: %s %s \t| Deck: %s %s", game.getPlayerADeck(), playerA, game.getPlayerBDeck(), playerB));
        playerA = game.getPlayerA().getHand().size() > 1 ? "cards" : "card";
        playerB = game.getPlayerB().getHand().size() > 1 ? "cards" : "card";
        System.out.println(String.format("Hand: %s %s \t| Hand: %s %s", game.getPlayerA().getHand().size(), playerA, game.getPlayerB().getHand().size(), playerB));
        System.out.println("**************************************");
    }
}
