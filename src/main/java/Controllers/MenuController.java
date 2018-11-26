package Controllers;

import Game.Game;
import NetworkClient.NetworkClient;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.util.Duration;
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
            NetworkClient.getInstance().sendMessageToServer("LOGIN " + email.getText());
            System.out.println("LOGIN " + email.getText());
            /*Thread t = new Thread(() -> {
                while(true) {
                    if(NetworkClient.getInstance().pollMessage().equals("STARTED")); {
                        try {
                            renderGame();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            startGame();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                        
                    }
                }
            });
            
            t.join();*/
            //renderGame();
        } else {
            System.out.println("No such email found");
        }
    }
    
    /*public void startGame() throws Exception {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            try {
                renderGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        pause.play();
    }*/
    
    @FXML
    void handleHighscoreButton() throws IOException {
        Pane game = FXMLLoader.load(getClass().getResource("/highscore.fxml"));
        Main2.primaryStage.setTitle("Candy Wars");
        Main2.primaryStage.setScene(new Scene(game, 1920, 1080));
        Main2.primaryStage.show();
    }
    
    @FXML
    void handleQuitButton(){
        Platform.exit();
        
    }



    public static void renderGame() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        Pane pane = loader.load(MenuController.class.getResource("/board.fxml").openStream());
        BoardController boardController = loader.getController();
        Game.getInstance().setBoardController(boardController);
        Main2.primaryStage.setScene(new Scene(pane, 1920, 1080));
        Main2.primaryStage.setMaximized(true);
        Main2.primaryStage.setFullScreen(true);
        Main2.primaryStage.show();





        /*FXMLLoader loader = FXMLLoader(MenuController.class.getResource("/board.fxml").openStream());

        Pane game = FXMLLoader.load(MenuController.class.getResource("/board.fxml"));

        //Pane game = FXMLLoader.load(MenuController.class.getResource("/board.fxml"));
        Main2.primaryStage.setTitle("Candy Wars");
        Main2.primaryStage.setScene(new Scene(game, 1920, 1080));
        Main2.primaryStage.setMaximized(true);
        Main2.primaryStage.setFullScreen(true);
        Main2.primaryStage.show();

        loader.load();

        BoardController boardController = loader.getController();
        Game.getInstance().setBoardController(boardController,Main2.primaryStage);*/

    }
    void createAccountButton() throws Exception {
        Pane game = FXMLLoader.load(getClass().getResource("/create-account.fxml"));
        Main2.primaryStage.setTitle("Candy Wars");
        Main2.primaryStage.setScene(new Scene(game, 1920, 1080));
        Main2.primaryStage.show();
    }
    @FXML
    void handleAccountButton() throws Exception {
        createAccountButton();
    }

}