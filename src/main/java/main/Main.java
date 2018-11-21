package main;


import board.Board;
import card.BasicCreatureCard;
import player.Player;
import repository.Database;
import repository.QueryHandler;
import server.NetworkServer;
import server.Server;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    private static int choice;
    private static Scanner sc = new Scanner(System.in);
    private static Server server = Server.getInstance();
    private static NetworkServer networkServer;
    
    private static void launch(String[] args) {

        Database db = new Database();
        QueryHandler q = new QueryHandler();
        try {
            db.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            networkServer = NetworkServer.getInstance();
        } catch(Exception e){
            System.out.println(e.getMessage());
            networkServer.stop();
        }
    }
    
    public void stop(){
        networkServer.stop();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}