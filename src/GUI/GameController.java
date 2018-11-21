package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable{
    @FXML
    private HBox playerHand;
    @FXML
    private HBox enemyHand;
    @FXML
    private Pane gameBoard;

    private List<Group> playerHandData;
    private List<Group> enemyHandData;

    @FXML
    private List<Data> data = new ArrayList<>();

    public GameController(HBox playerHand, HBox enemyHand, Pane gameBoard, List playerHandData, List enemyHandData, List data){
        this.playerHand = playerHand;
        this.enemyHand = enemyHand;
        this.gameBoard = gameBoard;
        this.playerHandData = playerHandData;
        this.enemyHandData = enemyHandData;
        this.data = data;

    }


   /* private void renderGameBoard(){

    }
    private void createData(){
        this.createHands();
        this.createGameBoard();
    }
    private void renderHands(){
        this.renderPlayerHand();
        this.renderEnemyHand();
    }
    private void renderPlayerHand(){
        playerHand.getChildren().clear();
        playerHand.getChildren().addAll(playerHandData);
    }
    private void renderEnemyHand(){
        enemyHand.getChildren().clear();
        enemyHand.getChildren().addAll(enemyHandData);
    }

    private void createHands(){
        this.createEnemyHand();
        this.createPlayerHand();
    }
    private void createEnemyHand(){
        enemyHandData.clear();


    }
    private void createPlayerHand(){
        //CODE
    }
    //END TURN LABEL
    @FXML
    private Label turnLabel;
    //ADVANCE TURNS
    @FXML
    private void handleTurnButtonAction(ActionEvent event) {
        if (gameHasStarted == true) {
            game.nextTurn();

            //This is the AI doing the turn action
            while (game.getCurrentPlayer() == game.getLastPlayer()) {
                UsableAction action = opponent.getAction(game.getCurrentPlayer());
                if (action == null) {
                    System.out.println("Warning: Opponent did not properly end turn");
                    break;
                }
                action.perform();
            }

            turnLabel.setText(String.format("Turn Number %d", game.getTurnNumber()));

            //reload data at the start of a new turn
            this.createData();
            this.render();
        }
    }
    //TARGETING
    public TargetAction nextAction;
    public void performNextAction(Targetable target) {
        nextAction.setTarget(target);
        nextAction.perform();
        this.createData();
        this.render();
    }
    public void markTargets(List<Card> targets) {
        List<Node> cardsInPlayerBattlefield = playerBattlefieldPane.getChildren();
        List<Node> cardsInOpponentBattlefield = opponentBattlefieldPane.getChildren();
        for (Card target : targets) {
            for(Node node : cardsInPlayerBattlefield) {
                if(node.getId().equals(String.format("card%d",target.getId())) == true) {
                    CardNodeBattlefield actionNode = (CardNodeBattlefield)node;
                    actionNode.createTargetButton();
                }
            }
            for(Node node : cardsInOpponentBattlefield) {
                if(node.getId().equals(String.format("card%d",target.getId())) == true) {
                    CardNodeBattlefield actionNode = (CardNodeBattlefield)node;
                    actionNode.createTargetButton();
                }
            }
        }
    }*/


    @FXML
    public void placePlayerHandCards() {
        for(int i = 0; i < data.size(); i++) {
            playerHand.getChildren().addAll();
        }
    }
    @FXML
    public void placeEnemyHandCards(){
        for(int i = 0; i < data.size(); i++) {
            enemyHand.getChildren().addAll();
        }
    }
    @FXML
    public void placePlayerTableCards() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
