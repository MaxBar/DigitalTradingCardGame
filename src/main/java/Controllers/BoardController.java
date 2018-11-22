package Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import main.Card;
//import main.Data;

import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardController {
    //public Data data = new Data();
    AnchorPane cardPane;
    Button btn;
    int choiceHandCard;
    int choiceTableCard;
    ArrayList<AnchorPane> hbox = new ArrayList<>();
    public ArrayList<Card> newCard= new ArrayList<>( Arrays.asList(
            new Card(5, "Gröt", "/img/background.jpg"),
            new Card(5, "Mullvad", "/img/candycard.jpg"),
            new Card(5, "djungelvrål", "/img/candycard.jpg"),
            new Card(5, "godis", "/img/candycard.jpg")
    ));
    public List<Card> tableCard= new ArrayList<>();


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
        doSomething();


    }

    void doSomething() throws IOException {
        playerHand.getChildren().clear();
        //Draw Player Hand
        for (int i = 0; i < newCard.size() ; i++) {
            btn = new Button(""+i);
            btn.setPrefSize(190, 200);
            btn.setOpacity(0);
           // newCard.get(i).setId(i);
            cardPane = FXMLLoader.load(getClass().getResource("/card.fxml"));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText(newCard.get(i).getName());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText(String.valueOf(newCard.get(i).getId()));
            ((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(newCard.get(i).getUrl()));

            cardPane.getChildren().add(btn);

            hbox.add(cardPane);
            //System.out.println(btn.getId());


            int finalI = i;
            btn.setOnAction(new EventHandler<ActionEvent>() {

                                public void handle(ActionEvent event) {
                                    //System.out.println(finalI);
                                    choiceHandCard = finalI;
                                    tableCard.add(newCard.get(finalI));
                                  //  System.out.println(tableCard.size());
                                    newCard.remove(finalI);
                                    System.out.println(tableCard.get(tableCard.size()-1).getName());
                                    try {
                                        drawTable();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }});

            playerHand.getChildren().add(i,cardPane);
        }

//        System.out.println(playerHand.getChildren().get(2));
        //Plats i HBoxen
        //  playerHand.getChildren().add(4,cardPane);


//        cardPane.getChildren().get()


        // playerHand.getChildren().add();
    }
    //Draw Player Table
    void drawTable() throws IOException{
        playerTable.getChildren().clear();
        doSomething();
//        if(playerTable.getChildren() != null) {
//            playerTable.getChildren().remove(0);
//        }
        for (int i = 0; i < tableCard.size(); i++) {
            System.out.println("in tableCard");
            btn = new Button("" + i);
            btn.setPrefSize(190, 200);
            btn.setOpacity(0);
            // newCard.get(i).setId(i);
            cardPane = FXMLLoader.load(getClass().getResource("/card.fxml"));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText(tableCard.get(i).getName());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText(String.valueOf(tableCard.get(i).getId()));
            ((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(tableCard.get(i).getUrl()));

            cardPane.getChildren().add(btn);

            //hbox.add(cardPane);
            //System.out.println(btn.getId());


            int finalI = i;
            btn.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent event) {
                    System.out.println(finalI);
                    choiceTableCard = finalI;
                }
            });

            playerTable.getChildren().add(i, cardPane);
        }
    }

}
