package menu;

import Game.Game;
import NetworkClient.NetworkClient;

import java.io.IOException;
import java.util.Scanner;

public class GameMenu {
    public void rootMenu() {
        Scanner sc = new Scanner(System.in);
        int quitMessage = 9;
        int choice = 0;
        do {
            //printBoard();
            System.out.println("---------- MENU ----------");
            /*if(server.getTurn() == playerA && server.getPlayers()[playerA].getHand().size() > 0
                    || server.getTurn() == playerB && server.getPlayers()[playerB].getHand().size() > 0 ) {*/
                System.out.println("1) Place card");
            /*}
            if(server.getRound() > 1 && server.getTurn() == playerA && server.getPlayerATableCards().size() > 0
                    || server.getRound() > 1 && server.getTurn() == playerB && server.getPlayerBTableCards().size() > 0) {*/
                System.out.println("2) Attack");
            //}
            System.out.println("3) End turn");
            System.out.println("9) Quit game");
            System.out.println("---------- **** ----------");
            choice = sc.nextInt();
            try {
                checkChoice(choice);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(choice != quitMessage);
    }
    
    private void checkChoice(int choice) throws IOException {
        Game game = Game.getInstance();
        if(game.getTurn() == game.getPlayer().getPlayerTurn()) {
            switch (choice) {
                case 1:
                    System.out.println("place");
                    break;
                case 2:
                    System.out.println("attack");
                    break;
                case 3:
                    NetworkClient.getInstance().sendMessageToServer("END_TURN");
                    //System.out.println("end");
                    break;
                case 9:
                    break;
            }
        }
    }
}
