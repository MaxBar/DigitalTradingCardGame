package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import repository.Database;
import repository.Database;
import repository.QueryHandler;

public class Main2 extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Database db = new Database();
        QueryHandler q = new QueryHandler();
        try {
            db.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Main2.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        primaryStage.setTitle("Candy Wars");
        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }



}
