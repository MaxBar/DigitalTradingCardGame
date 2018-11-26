package Controllers;

import Game.Game;
import NetworkClient.NetworkClient;
import javafx.application.Platform;
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
import main.Card;
import player.Player;
//import main.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class BoardController {
    //public Data data = new Data();
    Game game = Game.getInstance();
    AnchorPane cardPane;
    Button btn;
    int choiceEnemyCard;
    int choicePlayerCard;
    int choicePlayerHand;
    public ArrayList<Card> playerHandCards = new ArrayList<>( Arrays.asList(
            new Card("Marshmallow",4,"/img/candycard.jpg", "","","Can do something",2, 2,1),
            new Card("Djungelvrål",4,"/img/djungelvral.jpg", "DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Ferrari",4,"/img/ferrari.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Mars",4,"/img/mars.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Plopp",4,"/img/plopp.jpg","DIRECTATTACK", "","Can do something",2, 2,1)
    ));

    public ArrayList<Card> enemyHandCards= new ArrayList<>( Arrays.asList(
            new Card("Marshmallow",4,"/img/candycard.jpg", "DIRECTATTACK","","Can do something",2, 2,1),
            new Card("Djungelvrål",4,"/img/djungelvral.jpg", "DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Ferrari",4,"/img/ferrari.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Mars",4,"/img/mars.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Plopp",4,"/img/plopp.jpg","DIRECTATTACK", "","Can do something",2, 2,1)
    ));

    public ArrayList<Card> playerDeck = new ArrayList<>( Arrays.asList(
            new Card("Marshmallow",4,"/img/candycard.jpg", "DIRECTATTACK","","Can do something",2, 2,1),
            new Card("Ferrari",4,"/img/ferrari.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Mars",4,"/img/mars.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Plopp",4,"/img/plopp.jpg","DIRECTATTACK", "","Can do something",2, 2,1)
    ));

    public ArrayList<Card> playerGY = new ArrayList<>( Arrays.asList(
            new Card("Plopp",4,"/img/plopp.jpg","DIRECTATTACK", "","Can do something",2, 2,1)
    ));

    public ArrayList<Card> enemyDeck = new ArrayList<>( Arrays.asList(
            new Card("Ferrari",4,"/img/ferrari.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Mars",4,"/img/mars.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Plopp",4,"/img/plopp.jpg","DIRECTATTACK", "","Can do something",2, 2,1)
    ));

    public ArrayList<Card> enemyGY = new ArrayList<>( Arrays.asList(
            new Card("Marshmallow",4,"/img/candycard.jpg", "DIRECTATTACK","","Can do something",2, 2,1),
            new Card("Mars",4,"/img/mars.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Plopp",4,"/img/plopp.jpg","DIRECTATTACK", "","Can do something",2, 2,1)
    ));
    public List<Card> playerTableCards = new ArrayList<>();
    public List<Card> enemyTableCards = new ArrayList<>( Arrays.asList(
            new Card("Marshmallow",4,"/img/candycard.jpg", "DIRECTATTACK","","Can do something",2, 2,1),
            new Card("Djungelvrål",4,"/img/djungelvral.jpg", "DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Ferrari",4,"/img/ferrari.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Mars",4,"/img/mars.jpg","DIRECTATTACK", "","Can do something",2, 2,1),
            new Card("Plopp",4,"/img/plopp.jpg","DIRECTATTACK", "","Can do something",2, 2,1)


    ));

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

    @FXML Button endTurnButton;
    @FXML Button optionButton;
    @FXML Label playerGraveyardCard;
    @FXML Label playerDeckCard;
    @FXML Label enemyDeckCard;
    @FXML Label enemyGraveyardCard;


    public void initialize() throws IOException{
        //NetworkClient.getInstance().sendMessageToServer("STARTED");
        updateAll();
        end();
        enemyAttack();


    }
    void updateAll() throws IOException{


        drawPlayerHandCards();
        drawTable();
        drawEnemyHand();
        drawEnemyTable();
        updatePlayerHealth();
        updateEnemyHealth();
        updatePlayerMana();
        updateEnemyMana();
        updateDeck();
        updateGraveyard();
        end();

    }
    private void enemyAttack() throws IOException{
        btn = new Button();
        btn.setPrefSize(200, 206);
        btn.setOpacity(0);
        enemyBox.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //TODO send message to Server attacking enemy player, Needs to know if enemy is 0 or 1
                if(choicePlayerCard>-1) {
                    System.out.printf("You attack with %s on enemy", choicePlayerHand //needs to know turn so correct message to server will be send
                    );

                    choicePlayerCard = -10;
                    choiceEnemyCard = -10;
                }
                try {
                    updateAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }});
    }


    private void updateGraveyard() {
        playerGraveyardCard.setText("Grave:"+String.valueOf(playerGY.size()));
        enemyGraveyardCard.setText("Grave:"+String.valueOf(enemyGY.size()));
    }

    private void updateDeck() {
                playerDeckCard.setText("Deck:"+String.valueOf(playerDeck.size()));
                enemyDeckCard.setText("Deck:"+String.valueOf(enemyDeck.size()));
    }

    private void end() throws IOException{
        endTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //TODO send message to Server about switching round
                System.out.println("Server its time to switch turn!");
                try {
                    updateAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }});
    }

    private void updatePlayerHealth() {
        int getPlayerHp = 10;
        double hp = ((double)getPlayerHp/20);
        playerHP.setProgress(hp);
        playerHpLabel.setText("HP: " + getPlayerHp);
    }

    void updatePlayerMana() throws IOException{
        int getPlayerMana = 20;
        double mp = ((double)getPlayerMana/20);
        playerMana.setProgress(mp);
        playerMpLabel.setText("MP: " + getPlayerMana);

    }

    private void updateEnemyMana() throws IOException{
        int getEnemyMana = 20;
        double mp = ((double)getEnemyMana/20);
        enemyMana.setProgress(mp);
        enemyMpLabel.setText("MP: " + getEnemyMana);

    }
    void updateEnemyHealth() throws IOException{
        int getEnemyHP = 20;
        double hp = ((double)getEnemyHP/20);
        enemyHP.setProgress(hp);
        enemyHpLabel.setText("HP: " + getEnemyHP);

    }

    void drawPlayerHandCards() throws IOException {
        playerHand.getChildren().clear();
        //Draw Player Hand
        for (int i = 0; i < playerHandCards.size() ; i++) {
            btn = new Button(""+i);
            btn.setPrefSize(190, 200);
            btn.setOpacity(0);
            // playerHandCards.get(i).setId(i);
            cardPane = FXMLLoader.load(getClass().getResource("/cardMini.fxml"));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText("Card Name: " +playerHandCards.get(i).getName());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText("Mana Cost: "+String.valueOf(playerHandCards.get(i).getManaCost()));
            ((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(playerHandCards.get(i).getImage()));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ability")))).setText("Ability: " + playerHandCards.get(i).getAbility());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#special")))).setText("Special: " + playerHandCards.get(i).getSpecial());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#abilityDescription")))).setText("Description: " + playerHandCards.get(i).getFlavourText());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ap")))).setText("AP: " + String.valueOf(playerHandCards.get(i).getAttack()));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#dp")))).setText("DP: " +String.valueOf(playerHandCards.get(i).getDefense()));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#hp")))).setText("HP: " +String.valueOf(playerHandCards.get(i).getHealth()));
            cardPane.getChildren().add(btn);




            int finalI = i;
            btn.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent event) {
                    playerTableCards.add(playerHandCards.get(finalI));
                    choicePlayerHand = finalI;
                    //TODO Server message!
                    System.out.println("Server message picked" +choicePlayerHand);
                    playerHandCards.remove(finalI);
                    try {
                        updateAll();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }});

            playerHand.getChildren().add(i,cardPane);
        }
    }
    //Draw Player Table
    void drawTable() throws IOException{
        playerTable.getChildren().clear();
        for (int i = 0; i < playerTableCards.size(); i++) {

            btn = new Button("" + i);
            btn.setPrefSize(190, 200);
            btn.setOpacity(0);
            cardPane = FXMLLoader.load(getClass().getResource("/card.fxml"));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText("Card Name: " +playerTableCards.get(i).getName());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText("Mana Cost: "+String.valueOf(playerTableCards.get(i).getManaCost()));
            ((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(playerTableCards.get(i).getImage()));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ability")))).setText("Ability: " + playerTableCards.get(i).getAbility());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#special")))).setText("Special: " + playerTableCards.get(i).getSpecial());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#abilityDescription")))).setText("Description: " + playerTableCards.get(i).getFlavourText());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ap")))).setText("AP: " + String.valueOf(playerTableCards.get(i).getAttack()));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#dp")))).setText("DP: " +String.valueOf(playerTableCards.get(i).getDefense()));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#hp")))).setText("HP: " +String.valueOf(playerTableCards.get(i).getHealth()));

            cardPane.getChildren().add(btn);

            int finalI = i;
            btn.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent event) {
                    choicePlayerCard = finalI;
                }
            });

            playerTable.getChildren().add(i, cardPane);
        }
    }

    void drawEnemyHand() throws IOException {
        enemyHand.getChildren().clear();

        //Draw Player Hand
        for (int i = 0; i < enemyHandCards.size() ; i++) {
            btn = new Button(""+i);
            btn.setPrefSize(190, 200);
            btn.setOpacity(0);
            cardPane = FXMLLoader.load(getClass().getResource("/enemyCard.fxml"));
            enemyHand.getChildren().add(i,cardPane);
        }

    }

    void drawEnemyTable() throws IOException{
        enemyTable.getChildren().clear();
        for (int i = 0; i < enemyTableCards.size(); i++) {

            btn = new Button("" + i);
            btn.setPrefSize(190, 200);
            btn.setOpacity(0);
            cardPane = FXMLLoader.load(getClass().getResource("/card.fxml"));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText("Card Name: " +enemyTableCards.get(i).getName());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText("Mana Cost: "+String.valueOf(enemyTableCards.get(i).getManaCost()));
            ((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(enemyTableCards.get(i).getImage()));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ability")))).setText("Ability: " + enemyTableCards.get(i).getAbility());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#special")))).setText("Special: " + enemyTableCards.get(i).getSpecial());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#abilityDescription")))).setText("Description: " + enemyTableCards.get(i).getFlavourText());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#ap")))).setText("AP: " + String.valueOf(enemyTableCards.get(i).getAttack()));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#dp")))).setText("DP: " +String.valueOf(enemyTableCards.get(i).getDefense()));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#hp")))).setText("HP: " +String.valueOf(enemyTableCards.get(i).getHealth()));
            cardPane.getChildren().add(btn);




            int finalI = i;
            btn.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent event) {
                    if(choicePlayerCard>-1) {
                        choiceEnemyCard = finalI;
                        System.out.printf("%s attacked %s OMFG \n", choicePlayerCard, choiceEnemyCard);

                        choicePlayerCard = -10;
                        choiceEnemyCard = -10;
                    }

                    try {
                        updateAll();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }});

            enemyTable.getChildren().add(i, cardPane);
        }
    }

}
