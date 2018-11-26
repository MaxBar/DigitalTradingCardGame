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
import NetworkClient.NetworkClient;
import Game.Game;

import java.io.IOException;

public class Main2 extends Application {
    public static Stage primaryStage;
    private static NetworkClient client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Database db = new Database();

        try {
            client = NetworkClient.getInstance();
            //client = new NetworkClient("10.155.88.80", 150);
        } catch(Exception e){
            System.out.println(e.getMessage());
            client.stop();
        }

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
        inputThread.start();

    }

    private static Thread inputThread = new Thread() {
        public void run() {
            while (true) {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String text = client.pollMessage();
                    if (text != null) {
                        try {
                            Game.getInstance().receiveCommand(text);
                            System.out.println(text);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    };


    public static void main(String[] args) {
        //inputThread.start();
        launch(args);
    }



}
