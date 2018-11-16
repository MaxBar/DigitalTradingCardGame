package menu;

import Game.Game;
import NetworkClient.*;
import card.BasicCard;
import card.BasicCreatureCard;
import card.BasicMagicCard;
import card.SpecialAbilityCreatureCard;
import player.Player;

import java.io.IOException;
import java.util.Scanner;

public class GameMenu {
    Player player  = Game.getInstance().getPlayer();
    Game game = Game.getInstance();
    Scanner sc = new Scanner(System.in);
    public Thread rootMenu = new Thread() {
        public void run() {

            int quitMessage = 9;
            int choice;
            do {
                //printBoard();
                System.out.println("---------- MENU ----------");
    /*if(server.getTurn() == playerA && server.getPlayers()[playerA].getHand().size() > 0
            || server.getTurn() == playerB && server.getPlayers()[playerB].getHand().size() > 0 ) {*/
                System.out.println("1) Show hand");
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
            } while (choice != quitMessage);
        }
    };
        
        /*{
            public void run() {
                int choice;
        
                do {
                    //printBoard();
                    System.out.println("---------- MENU ----------");
        /*if(server.getTurn() == playerA && server.getPlayers()[playerA].getHand().size() > 0
                || server.getTurn() == playerB && server.getPlayers()[playerB].getHand().size() > 0 ) {*/
                    //System.out.println("1) Place card");
        /*}
        if(server.getRound() > 1 && server.getTurn() == playerA && server.getPlayerATableCards().size() > 0
                || server.getRound() > 1 && server.getTurn() == playerB && server.getPlayerBTableCards().size() > 0) {*/
                    //System.out.println("2) Attack");
                    //}
                    /*System.out.println("3) End turn");
                    System.out.println("9) Quit game");
                    System.out.println("---------- **** ----------");
                    choice = sc.nextInt();
                    try {
                        checkChoice(choice);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (choice != quitMessage);
            }
        });*/
    //}
    
    private void checkChoice(int choice) throws IOException {
        //Game game = Game.getInstance();
        if(Game.getInstance().getTurn() == Game.getInstance().getPlayer().getPlayerTurn()) {
            switch (choice) {
                case 1:
                    System.out.println("place");
                    showHand();
                    break;
                case 2:
                    System.out.println("attack");
                    break;
                case 3:
                    NetworkClient.getInstance().sendMessageToServer("END_TURN");
                    System.out.println("end");
                    break;
                case 9:
                    break;
            }
        }
    }

    public void showHand() throws IOException {
        System.out.println(String.format("---------- SHOW %s's HAND ----------", Game.getInstance().getPlayer().getName()));

        for (int i = 0; i < Game.getInstance().getPlayer().getHand().size() ; i++) {
            if(player.getHand().get(i)instanceof BasicCreatureCard){
                var card = (BasicCreatureCard)player.getHand().get(i);
                System.out.printf((i + 1) + ") %s AP: %s DP: %s HP: %s Mana cost: %s\n",
                        player.getHand().get(i).getName(),
                        card.getAttack(),
                        card.getDefense(),
                        card.getHealth(),
                        card.getManaCost());
            }else if(player.getHand().get(i)instanceof SpecialAbilityCreatureCard){
                var card  = (SpecialAbilityCreatureCard)player.getHand().get(i);
                System.out.printf((i + 1) + ") %s Ability: %s AP: %s DP: %s HP: %s Mana cost: %s\n",
                        player.getHand().get(i).getName(),
                        card.getAbilityDescription(),
                        card.getAttack(),
                        card.getDefense(),
                        card.getHealth(),
                        card.getManaCost());

            }else if(player.getHand().get(i)instanceof BasicMagicCard){
                var card = (BasicMagicCard)player.getHand().get(i);
                System.out.printf((i + 1) + ") %s Ability: %s Mana cost: %s\n",
                        player.getHand().get(i).getName(),
                        card.getAbilityDescription(),
                        card.getManaCost());
            }

        }
        System.out.printf("%s) Back", player.getHand().size() + 1);

        int choice = sc.nextInt() - 1;
        if(choice < player.getHand().size()){
            if(player.getHand().get(choice)instanceof BasicMagicCard) {
                NetworkClient.getInstance().sendMessageToServer(String.format("USE P%s MAGIC_CREATURE %s", Game.getInstance().getTurn(), choice));
            }else{
                NetworkClient.getInstance().sendMessageToServer(String.format("PLACE P%s_CREATURE %s ", Game.getInstance().getTurn(), choice));
            }
        }

       System.out.println("---------- *********** ----------");
//        //TODO Place card in next available spot
//
//        if (message.equals("SUCCESS")) {
//            System.out.println("You placed card: " + cardName);
//        } else {
//            System.out.println("There is no more room to place your card");
//        }
    }

    public void showAttackCards() throws IOException {
        System.out.println(String.format("---------- SHOW %s's ATTACK CHOICES ----------", Game.getInstance().getPlayer().getName()));

        for (int i = 0; i < game.getPlayerTableCards().size() ; i++) {
            if(game.getPlayerTableCards().get(i)instanceof BasicCreatureCard && !game.getPlayerTableCards().get(i).getIsConsumed()){
                var card = (BasicCreatureCard)player.getHand().get(i);
                System.out.printf((i + 1) + ") %s AP: %s DP: %s HP: %s\n",
                        player.getHand().get(i).getName(),
                        card.getAttack(),
                        card.getDefense(),
                        card.getHealth());
            }else if(game.getPlayerTableCards().get(i)instanceof SpecialAbilityCreatureCard && !game.getPlayerTableCards().get(i).getIsConsumed()){
                var card  = (SpecialAbilityCreatureCard)player.getHand().get(i);
                System.out.printf((i + 1) + ") %s Ability: %s AP: %s DP: %s HP: %s\n",
                        player.getHand().get(i).getName(),
                        card.getAbilityDescription(),
                        card.getAttack(),
                        card.getDefense(),
                        card.getHealth());
            }

        }
        System.out.printf("%s) Back", game.getPlayerTableCards().size() + 1);

        int choice = sc.nextInt() - 1;
        if(choice < game.getPlayerTableCards().size() && game.getEnemyTableCards().size() > 0){
           NetworkClient.getInstance().sendMessageToServer(String.format("ATTACK P%s ENEMY_CREATURE %s", Game.getInstance().getTurn(), choice));
        }else{
            NetworkClient.getInstance().sendMessageToServer(String.format("ATTACK P%s ENEMY_PLAYER %s", Game.getInstance().getTurn(), choice));
        }

    }

}
