package Controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import main.Main;
import main.Main2;
import repository.QueryHandler;

import java.io.IOException;


public class MenuController {
    QueryHandler queryHandler = new QueryHandler();
    @FXML
    TextField email;
    
    @FXML
    void handleStartButton() throws Exception {
        if(queryHandler.checkPlayerEmail(email.getText())) {
            renderGame();
        } else {
            System.out.println("No such email found");
        }
    }
    
    @FXML
    void handleHighscoreButton() throws IOException {
        Pane game = FXMLLoader.load(getClass().getResource("/highscore.fxml"));
        Main2.primaryStage.setTitle("Candy Wars");
        Main2.primaryStage.setScene(new Scene(game, 1280, 720));
        Main2.primaryStage.show();
    }
    
    @FXML
    void handleQuitButton(){
        Platform.exit();
        
    }
    public void renderGame() throws Exception {
        Pane game = FXMLLoader.load(getClass().getResource("/board.fxml"));
        Main2.primaryStage.setTitle("Candy Wars");
        Main2.primaryStage.setScene(new Scene(game, 1280, 720));
        Main2.primaryStage.show();
    }
    void createAccountButton() throws Exception {
        Pane game = FXMLLoader.load(getClass().getResource("/create-account.fxml"));
        Main2.primaryStage.setTitle("Candy Wars");
        Main2.primaryStage.setScene(new Scene(game, 1280, 720));
        Main2.primaryStage.show();
    }
    @FXML
    void handleAccountButton() throws Exception {
        createAccountButton();
    }

}