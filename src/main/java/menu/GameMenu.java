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
    //Player player  = Game.getInstance();
    Game game = Game.getInstance();
    public Thread rootMenu = new Thread() {
        public void run() {
            Scanner sc = new Scanner(System.in);

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
                    showAttackCards();
                    break;
                case 3:
                    NetworkClient.getInstance().sendMessageToServer("END_TURN");
                    System.out.println("---------- ENEMY's TURN ----------\n");
                    break;
                case 9:
                    break;
            }
        }
    }

    public void showHand() throws IOException {
        System.out.println(String.format("---------- SHOW %s's HAND ----------", Game.getInstance().getPlayer().getName()));

        for (int i = 0; i < game.getPlayer().getHand().size() ; ++i) {
            if (game.getPlayer().getHand().get(i) != null) {
                if (game.getPlayer().getHand().get(i) instanceof SpecialAbilityCreatureCard) {
                    var card = (SpecialAbilityCreatureCard) game.getPlayer().getHand().get(i);
                    System.out.printf((i + 1) + ") %s Ability: %s AP: %s DP: %s HP: %s Mana cost: %s\n",
                            game.getPlayer().getHand().get(i).getName(),
                            card.getAbilityDescription(),
                            card.getAttack(),
                            card.getDefense(),
                            card.getHealth(),
                            card.getManaCost());
                } else if (game.getPlayer().getHand().get(i) instanceof BasicCreatureCard) {
                    var card = (BasicCreatureCard) game.getPlayer().getHand().get(i);
                    System.out.printf((i + 1) + ") %s AP: %s DP: %s HP: %s Mana cost: %s\n",
                            game.getPlayer().getHand().get(i).getName(),
                            card.getAttack(),
                            card.getDefense(),
                            card.getHealth(),
                            card.getManaCost());
                    System.out.flush();
                } else if (game.getPlayer().getHand().get(i) instanceof BasicMagicCard) {
                    var card = (BasicMagicCard) game.getPlayer().getHand().get(i);
                    System.out.printf((i + 1) + ") %s Ability: %s Mana cost: %s\n",
                            game.getPlayer().getHand().get(i).getName(),
                            //card.getAbilityDescription(),
                            String.format(card.getAbilityDescription(), ((BasicMagicCard) game.getPlayer().getHand().get(i)).getAbilityValue()),
                            card.getManaCost());
                }
            }
        }

        System.out.printf("%s) Back\n", game.getPlayer().getHand().size() + 1);
        Scanner sc = new Scanner(System.in);

        int choice;
        sc.nextLine();
        choice = sc.nextInt() - 1;
        if (choice == game.getPlayer().getHand().size() + 1) {
            return;
        }
        if(choice < game.getPlayer().getHand().size()){
            if(game.getPlayer().getHand().get(choice)instanceof BasicMagicCard) {
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
        Scanner sc = new Scanner(System.in);
        System.out.println(String.format("---------- SHOW %s's ATTACK CHOICES ----------", Game.getInstance().getPlayer().getName()));

        for (int i = 0; i < game.getPlayerTableCards().size() ; i++) {


            if(game.getPlayerTableCards().get(i)instanceof SpecialAbilityCreatureCard && !game.getPlayerTableCards().get(i).getIsConsumed()){
                var card  = (SpecialAbilityCreatureCard)game.getPlayerTableCards().get(i);
                System.out.printf((i + 1) + ") %s Ability: %s AP: %s DP: %s HP: %s\n",
                        game.getPlayerTableCards().get(i).getName(),
                        card.getAbilityDescription(),
                        card.getAttack(),
                        card.getDefense(),
                        card.getHealth());
            }else if(game.getPlayerTableCards().get(i)instanceof BasicCreatureCard && !game.getPlayerTableCards().get(i).getIsConsumed()){
                var card = (BasicCreatureCard)game.getPlayerTableCards().get(i);
                System.out.printf((i + 1) + ") %s AP: %s DP: %s HP: %s\n",
                        game.getPlayerTableCards().get(i).getName(),
                        card.getAttack(),
                        card.getDefense(),
                        card.getHealth());
            }

        }
        System.out.printf("%s) Back\n", game.getPlayerTableCards().size() + 1);

        int choice = sc.nextInt() - 1;
        if (choice == game.getPlayerTableCards().size()) {
            System.out.println("inget");
        } else if(choice < game.getPlayerTableCards().size() && game.getEnemyTableCards().size() > 0){
           NetworkClient.getInstance().sendMessageToServer(String.format("ATTACK P%s ENEMY_CREATURE %s", Game.getInstance().getTurn(), choice));
        }else{
            NetworkClient.getInstance().sendMessageToServer(String.format("ATTACK P%s ENEMY_PLAYER %s", Game.getInstance().getTurn(), choice));
        }

    }

    public static void printBoard() {
        Game game = Game.getInstance();
        int tableSize = 5;
        System.out.println("**************************************");
        System.out.printf("%s - HP: %s MP: %s \t\t| ENEMY - HP: %s MP: %s\n",
                Game.getInstance().getPlayer().getName(), Game.getInstance().getPlayer().getHealth(), game.getPlayer().getMana(),
                Game.getInstance().getEnemyHealth(), game.getEnemyMana());
        System.out.println("--------------------------------------");
        for(int i = 0; i < tableSize; ++i) {
            String finalString = "";
            if(game.getPlayerTableCards().size() - 1 >= i) {
                finalString = String.format("%-12s HP: %s \t| ", game.getPlayerTableCards().get(i).getName(), ((BasicCreatureCard)game.getPlayerTableCards().get(i)).getHealth());
            } else {
                finalString = "EMPTY \t\t\t\t| ";
            }
            if(game.getEnemyTableCards().size() - 1 >= i) {
                finalString += String.format("%-12s HP: %s", game.getEnemyTableCards().get(i).getName(), ((BasicCreatureCard)game.getEnemyTableCards().get(i)).getHealth());
            } else {
                finalString += "EMPTY";
            }
            System.out.println(finalString);
        }
        System.out.println("--------------------------------------");
        String player = game.getPlayerGraveyard() > 1 ? "cards" : "card";
        String enemy = game.getEnemyGraveyard() > 1 ? "cards" : "card";
        System.out.println(String.format("Graveyard: %s %s \t| Graveyard: %s %s", game.getPlayerGraveyard(), player, game.getEnemyGraveyard(), enemy));
        player = game.getPlayerDeck() > 1 ? "cards" : "card";
        enemy = game.getEnemyDeck() > 1 ? "cards" : "card";
        System.out.println(String.format("Deck: %s %s \t\t| Deck: %s %s", game.getPlayerDeck(), player, game.getEnemyDeck(), enemy));
        player = game.getPlayer().getHand().size() > 1 ? "cards" : "card";
        enemy = game.getEnemyHand() > 1 ? "cards" : "card";
        System.out.println(String.format("Hand: %s %s \t\t| Hand: %s %s", game.getPlayer().getHand().size(), player, game.getEnemyHand(), enemy));
        System.out.println("**************************************");
    }

}
