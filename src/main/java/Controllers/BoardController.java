package Controllers;

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
    public List<Card> newCard= Arrays.asList(
            new Card(5, "Gröt", "/img/background.jpg"),
            new Card(2, "Mullvad", "/img/candycard.jpg")
    );


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

        for (int i = 0; i < 2 ; i++) {
            cardPane = FXMLLoader.load(getClass().getResource("/card.fxml"));
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardName")))).setText(newCard.get(i).getName());
            ((Label) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardManaCost")))).setText(String.valueOf(newCard.get(i).getId()));
            ((ImageView) cardPane.getChildren().get(cardPane.getChildren().indexOf(cardPane.lookup("#cardImg")))).setImage(new Image(newCard.get(i).getUrl()));
            playerHand.getChildren().add(cardPane);
        }
        //Plats i HBoxen
        //  playerHand.getChildren().add(4,cardPane);


//        cardPane.getChildren().get()


        // playerHand.getChildren().add();
    }

}
