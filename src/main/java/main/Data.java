package main;

import java.util.ArrayList;
import java.util.List;

public class Data {
    List<Card> playerHand = new ArrayList<>();
    List<Card> playerTable = new ArrayList<>();
    List<Card> enemyHand = new ArrayList<>();
    List<Card> enemyTable = new ArrayList<>();

    Data(){
        playerHand.add(new Card(1, "Salt Fisk"));
        playerHand.add(new Card(2, "Fidelur"));
        playerHand.add(new Card(3, "Häxvrål"));
        playerHand.add(new Card(4, "lille John"));
        playerHand.add(new Card(5, "Palle Kulling"));


        enemyHand.add(new Card(6, "Dumle"));
        enemyHand.add(new Card(7, "Marshmallow"));
        enemyHand.add(new Card(8, "Djungelvrål"));
        enemyHand.add(new Card(9, "Turkisk Peppar"));
        enemyHand.add(new Card(10, "Kola"));
    }
}
