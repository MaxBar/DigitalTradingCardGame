package Controllers;

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
//import main.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardController {
    //public Data data = new Data();
    AnchorPane cardPane;
    Button btn;
    int choiceEnemyCard;
    int choicePlayerCard;
    ArrayList<AnchorPane> hbox = new ArrayList<>();
    public ArrayList<Card> playerHandCards = new ArrayList<>( Arrays.asList(
            new Card(5, "Gröt", "/img/background.jpg"),
            new Card(5, "Mullvad", "/img/candycard.jpg"),
            new Card(5, "djungelvrål", "/img/candycard.jpg"),
            new Card(5, "godis", "/img/candycard.jpg")
    ));
    public ArrayList<Card> enemyHandCards= new ArrayList<>( Arrays.asList(
            new Card(5, "Gröt", "/img/background.jpg"),
            new Card(5, "Mullvad", "/img/candycard.jpg"),
            new Card(5, "djungelvrål", "/img/candycard.jpg"),
            new Card(5, "godis", "/img/candycard.jpg")
    ));
    public List<Card> playerTableCards = new ArrayList<>();
    public List<Card> enemyTableCards = new ArrayList<>( Arrays.asList(
            new Card(5, "Gröt", "/img/background.jpg"),
            new Card(5, "Mullvad", "/img/candycard.jpg"),
            new Card(5, "djungelvrål", "/img/candycard.jpg"),
            new Card(5, "godis", "/img/candycard.jpg")
    ));

    @FXML HBox playerHand;
    @FXML HBox playerTable;

    @FXML HBox enemyHand;
    @FXML HBox enemyTable;
    @FXML Button endTurnButton;
    @FXML Button optionButton;
    @FXML ProgressBar playerHP;
    @FXML ProgressBar playerMana;
    @FXML ProgressBar enemyHP;
    @FXML ProgressBar enemyMana;
    @FXML Label playerGraveyardNr;
    @FXML Label playerDeckNr;
    @FXML Label enemyDeckNr;
    @FXML Label enemyGraveyardNr;


    public void initialize() throws IOException{
        drawPlayerHandCards();
        drawEnemyHand();
        drawEnemyTable();



    }

    void drawPlayerHandCards() throws IOException {
        playerHand.getChildren().clear();
        //Draw Player Hand
        for (int i = 0; i < playerHandCards.size() ; i++) {
            btn = new Button(""+i);
            btn.setPrefSize(190, 200);
            btn.setOpacity(0);
           // playerHandCards.get(i).setId(i);
            cardPane = FXMLLoader.load(getClass().getResource("/card.fxml"));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText(playerHandCards.get(i).getName());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText(String.valueOf(playerHandCards.get(i).getId()));
            ((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(playerHandCards.get(i).getUrl()));

            cardPane.getChildren().add(btn);

            hbox.add(cardPane);



            int finalI = i;
            btn.setOnAction(new EventHandler<ActionEvent>() {

                                public void handle(ActionEvent event) {

                                    System.out.println(finalI);
                                    playerTableCards.add(playerHandCards.get(finalI));
                                    playerHandCards.remove(finalI);
                                    try {
                                        drawTable();
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
        drawPlayerHandCards();
        for (int i = 0; i < playerTableCards.size(); i++) {

            btn = new Button("" + i);
            btn.setPrefSize(190, 200);
            btn.setOpacity(0);
            cardPane = FXMLLoader.load(getClass().getResource("/card.fxml"));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText(playerTableCards.get(i).getName());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText(String.valueOf(playerTableCards.get(i).getId()));
            ((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(playerTableCards.get(i).getUrl()));

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
        drawPlayerHandCards();
        for (int i = 0; i < enemyTableCards.size(); i++) {

            btn = new Button("" + i);
            btn.setPrefSize(190, 200);
            btn.setOpacity(0);
            cardPane = FXMLLoader.load(getClass().getResource("/card.fxml"));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText(enemyTableCards.get(i).getName());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText(String.valueOf(enemyTableCards.get(i).getId()));
            ((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(enemyTableCards.get(i).getUrl()));

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
                        drawTable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }});

            enemyTable.getChildren().add(i, cardPane);
        }
    }

}
