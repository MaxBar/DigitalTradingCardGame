package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/board.fxml"));
        primaryStage.setTitle("Candy Wars");
        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


//   public void renderGame(Stage gameStage) throws Exception {
//        Parent game = FXMLLoader.load(getClass().getResource("board.fxml"));
//        gameStage.setTitle("Candy Wars");
//        gameStage.setScene(new Scene(game, 1280, 720));
//        gameStage.show();
//    }
}
