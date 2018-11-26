package Controllers;

import Game.Game;
import NetworkClient.NetworkClient;
import card.BasicCreatureCard;
import card.BasicMagicCard;
import card.SpecialAbilityCreatureCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//import main.Data;

import java.io.IOException;

public class BoardController {
    //public Data data = new Data();
    Game game = Game.getInstance();
    Stage stage;
    AnchorPane cardPane;
    Button btn;
    int choiceEnemyCard;
    int choicePlayerCard;
    int choicePlayerHand;

    //HBOX
    @FXML HBox playerHand;
    @FXML HBox playerTable;
    @FXML HBox enemyHand;
    @FXML HBox enemyTable;
    //PlayerBox
    @FXML AnchorPane playerBox;
    @FXML AnchorPane enemyBox;
    //LABELS
    @FXML Label playerHpLabel;
    @FXML Label playerMpLabel;
    @FXML Label enemyHpLabel;
    @FXML Label enemyMpLabel;
    //Progressbar
    @FXML ProgressBar playerHP;
    @FXML ProgressBar playerMana;
    @FXML ProgressBar enemyHP;
    @FXML ProgressBar enemyMana;

    @FXML Label labelTurn;
    @FXML Label playerLabelTurn;
    @FXML Label enemyLabelTurn;

    @FXML Button endTurnButton;
    @FXML Button optionButton;
    @FXML Label playerGraveyardCard;
    @FXML Label playerDeckCard;
    @FXML Label enemyDeckCard;
    @FXML Label enemyGraveyardCard;


    @FXML
    public void initialize() throws IOException{
        //NetworkClient.getInstance().sendMessageToServer("STARTED");
        updateAll();
        endTurn();
        attackEnemyPlayer();
    }

    @FXML
    public void updateAll() throws IOException {
        updatePlayerHealth();
        updateEnemyHealth();
        updatePlayerMana();
        updateEnemyMana();
        drawPlayerHandCards();
        drawPlayerTableCards();
        drawEnemyHand();
        drawEnemyTableCards();
        updateDeck();
        updateGraveyard();
        drawPlayerHandCards();
        drawPlayerTableCards();
        drawEnemyHand();
        drawEnemyTableCards();
        drawWhosTurn();
        endTurn();
    }

    private void drawWhosTurn() {
        if(game.getPlayer().getPlayerTurn() == game.getTurn()){
            playerLabelTurn.setText("YOUR TURN");
            enemyLabelTurn.setText("");
        } else {
            enemyLabelTurn.setText("ENEMY TURN");
            playerLabelTurn.setText("");
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private void attackEnemyPlayer() throws IOException{
        btn = new Button();
        btn.setPrefSize(200, 206);
        btn.setOpacity(0);
        enemyBox.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (game.getPlayer().getPlayerTurn() == game.getTurn()) {
                    //TODO send message to Server attacking enemy player, Needs to know if enemy is 0 or 1
                    if (choicePlayerCard >= 0) {
                        try {
                            NetworkClient.getInstance().sendMessageToServer(String.format("ATTACK P%s ENEMY_PLAYER %s", Game.getInstance().getTurn(), choicePlayerCard));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }});
    }

    private void updateGraveyard() {
        playerGraveyardCard.setText("Grave:"+String.valueOf(game.getPlayerGraveyard()));
        enemyGraveyardCard.setText("Grave:"+String.valueOf(game.getEnemyGraveyard()));
    }

    private void updateDeck() {
                playerDeckCard.setText("Deck:"+String.valueOf(game.getPlayerDeck()));
                enemyDeckCard.setText("Deck:"+String.valueOf(game.getEnemyDeck()));
    }

    private void endTurn() throws IOException{
        endTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (game.getPlayer().getPlayerTurn() == game.getTurn()) {
                    try {
                        NetworkClient.getInstance().sendMessageToServer("END_TURN");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }});
    }

    private void updatePlayerHealth() {
        if (game.getPlayer() != null) {
            double hp = ((double) game.getPlayer().getHealth() / 20);
            playerHP.setProgress(hp);
            playerHpLabel.setText("HP: " + game.getPlayer().getHealth());
        }
    }

    void updatePlayerMana() throws IOException {
        if (game.getPlayer() != null) {
            //TODO Set player maxMana
            double mp = ((double) game.getPlayer().getMana() /game.getRound());
            playerMana.setProgress(mp);
            playerMpLabel.setText("MP: " + game.getPlayer().getMana());
        }
    }

    private void updateEnemyMana() throws IOException {
        double mp = ((double)game.getEnemyMana()/game.getRound());
        enemyMana.setProgress(mp);
        enemyMpLabel.setText("MP: " + game.getEnemyMana());
    }

    void updateEnemyHealth() throws IOException {
        double hp = ((double)game.getEnemyHealth()/20);
        enemyHP.setProgress(hp);
        enemyHpLabel.setText("HP: " + game.getEnemyHealth());
    }

    void drawPlayerHandCards() throws IOException {
        System.out.println("Player hand " + playerHand);
        if (playerHand != null) {
            playerHand.getChildren().clear();
        }
        //Draw Player Hand
        if (game.getPlayer() != null) {
            for (int i = 0; i < game.getPlayer().getHand().size(); i++) {
                btn = new Button("" + i);
                btn.setPrefSize(190, 200);
                btn.setOpacity(0);
                // playerHandCards.get(i).setId(i);
                cardPane = FXMLLoader.load(getClass().getResource("/cardMini.fxml"));
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText( game.getPlayer().getHand().get(i).getName());
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText("MC: " + String.valueOf(game.getPlayer().getHand().get(i).getManaCost()));
                //((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(game.getPlayer().getHand().get(i).getImage()));
                if (game.getPlayer().getHand().get(i) instanceof BasicMagicCard) {
                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ability")))).setText(((BasicMagicCard) game.getPlayer().getHand().get(i)).getKeyword().name());
                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#abilityDescription")))).setText(((BasicMagicCard) game.getPlayer().getHand().get(i)).getAbilityDescription());

                } else if (game.getPlayer().getHand().get(i) instanceof SpecialAbilityCreatureCard) {
                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#special")))).setText(((SpecialAbilityCreatureCard) game.getPlayer().getHand().get(i)).getKeyword().name());
                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#abilityDescription")))).setText(((SpecialAbilityCreatureCard) game.getPlayer().getHand().get(i)).getAbilityDescription());
                }
                if (game.getPlayer().getHand().get(i) instanceof BasicMagicCard) {

                } else {

                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ap")))).setText("AP: " + String.valueOf(((BasicCreatureCard) game.getPlayer().getHand().get(i)).getAttack()));
                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#dp")))).setText("DP: " + String.valueOf(((BasicCreatureCard) game.getPlayer().getHand().get(i)).getDefense()));
                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#hp")))).setText("HP: " + String.valueOf(((BasicCreatureCard) game.getPlayer().getHand().get(i)).getHealth()));
                }
                cardPane.getChildren().add(btn);

                int finalI = i;
                btn.setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        choicePlayerHand = finalI;
                        //TODO Server message!
                        // Add to hand
                        if (game.getPlayer().getPlayerTurn() == game.getTurn()) {
                            System.out.println("Server message picked" + choicePlayerHand);
                            if (game.getPlayer().getHand().get(choicePlayerHand) instanceof BasicCreatureCard) {
                                try {
                                    NetworkClient.getInstance().sendMessageToServer(String.format("PLACE P%s_CREATURE %s ", Game.getInstance().getTurn(), choicePlayerHand));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (game.getPlayer().getHand().get(choicePlayerHand) instanceof BasicMagicCard) {
                                try {
                                    NetworkClient.getInstance().sendMessageToServer(String.format("USE P%s MAGIC_CREATURE %s", Game.getInstance().getTurn(), choicePlayerHand));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });


                playerHand.getChildren().add(i, cardPane);

            }
        }
    }
    //Draw Player Table
    void drawPlayerTableCards() throws IOException {
        playerTable.getChildren().clear();
        if (game.getPlayerTableCards() != null) {
            for (int i = 0; i < game.getPlayerTableCards().size(); i++) {

                btn = new Button("" + i);
                btn.setPrefSize(190, 200);
                btn.setOpacity(0);
                cardPane = FXMLLoader.load(getClass().getResource("/card.fxml"));
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText(game.getPlayerTableCards().get(i).getName());
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText("MC: " + String.valueOf(game.getPlayerTableCards().get(i).getManaCost()));
                //((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(game.getPlayerTableCards().get(i).getImage()));
                //((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ability")))).setText("Ability: " + game.getPlayerTableCards().get(i).getAbility());
                if (game.getPlayerTableCards().get(i) instanceof SpecialAbilityCreatureCard) {
                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#special")))).setText(((SpecialAbilityCreatureCard) game.getPlayerTableCards().get(i)).getKeyword().name());
                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#abilityDescription")))).setText(((SpecialAbilityCreatureCard) game.getPlayerTableCards().get(i)).getAbilityDescription());

                }
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ap")))).setText("AP: " + String.valueOf(((BasicCreatureCard) game.getPlayerTableCards().get(i)).getAttack()));
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#dp")))).setText("DP: " + String.valueOf(((BasicCreatureCard) game.getPlayerTableCards().get(i)).getDefense()));
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#hp")))).setText("HP: " + String.valueOf(((BasicCreatureCard) game.getPlayerTableCards().get(i)).getHealth()));

                cardPane.getChildren().add(btn);

                int finalI = i;
                btn.setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        if (game.getPlayer().getPlayerTurn() == game.getTurn()) {
                            choicePlayerCard = finalI;
                        }
                    }
                });

                playerTable.getChildren().add(i, cardPane);
            }
        }
    }

    void drawEnemyHand() throws IOException {
        enemyHand.getChildren().clear();

        //Draw Player Hand
        for (int i = 0; i < game.getEnemyHand(); i++) {
            btn = new Button(""+i);
            btn.setPrefSize(190, 200);
            btn.setOpacity(0);
            cardPane = FXMLLoader.load(getClass().getResource("/enemyCard.fxml"));
            enemyHand.getChildren().add(i,cardPane);
        }
    }

    void drawEnemyTableCards() throws IOException {
        enemyTable.getChildren().clear();
        if (game.getEnemyTableCards() != null) {
            for (int i = 0; i < game.getEnemyTableCards().size(); i++) {

                btn = new Button("" + i);
                btn.setPrefSize(190, 200);
                btn.setOpacity(0);
                cardPane = FXMLLoader.load(getClass().getResource("/card.fxml"));
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText(game.getEnemyTableCards().get(i).getName());
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText("MC: " + String.valueOf(game.getEnemyTableCards().get(i).getManaCost()));
                //((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(game.getEnemyTableCards().get(i).getImage()));
                //((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ability")))).setText("Ability: " + game.getPlayerTableCards().get(i).getAbility());
                if (game.getEnemyTableCards().get(i) instanceof SpecialAbilityCreatureCard) {
                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#special")))).setText(((SpecialAbilityCreatureCard) game.getEnemyTableCards().get(i)).getKeyword().name());
                    ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#abilityDescription")))).setText(((SpecialAbilityCreatureCard) game.getEnemyTableCards().get(i)).getAbilityDescription());

                }
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ap")))).setText("AP: " + String.valueOf(((BasicCreatureCard) game.getEnemyTableCards().get(i)).getAttack()));
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#dp")))).setText("DP: " + String.valueOf(((BasicCreatureCard) game.getEnemyTableCards().get(i)).getDefense()));
                ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#hp")))).setText("HP: " + String.valueOf(((BasicCreatureCard) game.getEnemyTableCards().get(i)).getHealth()));

                cardPane.getChildren().add(btn);
                int finalI = i;
                btn.setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        if (game.getPlayer().getPlayerTurn() == game.getTurn()) {
                            if (choicePlayerCard >= 0) {
                                choiceEnemyCard = finalI;
                                try {
                                    NetworkClient.getInstance().sendMessageToServer(String.format("ATTACK P%s ENEMY_CREATURE %s %s", Game.getInstance().getTurn(), choicePlayerCard, choiceEnemyCard));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
                enemyTable.getChildren().add(i, cardPane);
            }
        }
    }
}
