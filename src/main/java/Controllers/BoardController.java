package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import main.Data;

import java.util.ArrayList;
import java.util.List;

public class BoardController {
    public Data player = new Data();
    private List<Button> buttonList = new ArrayList<>();

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

    doSomething(){
        playerHand.getChildren().add()
    }

}
