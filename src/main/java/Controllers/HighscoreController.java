package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HighscoreController {
    @FXML
    TableView highscoreList;
    
    //TODO CREATE HIGHSCORE PERSON WITH ALL OF BELOW VALUES WITH PROPER GET AND SET NAMES
    //TODO CREATE QUERY THAT GETS ALL PLAYERS AND CREATE NEW HIGHSCORE PLAYERS FOR EACH PLAYER FETCHED
    //TODO ADD TO HIGHSCORELIST SEE ROW 32
    public void setHighScoreList() {
        TableColumn name = new TableColumn();
        name.setText("Name");
        name.setCellValueFactory(new PropertyValueFactory("Name"));
        TableColumn wins = new TableColumn("Wins");
        name.setText("Wins");
        name.setCellValueFactory(new PropertyValueFactory("Wins"));
        TableColumn draws = new TableColumn("Draws");
        name.setText("Draws");
        name.setCellValueFactory(new PropertyValueFactory("Draws"));
        TableColumn losses = new TableColumn("Losses");
        name.setText("Losses");
        name.setCellValueFactory(new PropertyValueFactory("Losses"));
        TableColumn points = new TableColumn("Points");
        name.setText("Points");
        name.setCellValueFactory(new PropertyValueFactory("Points"));
        highscoreList.getColumns().addAll(name, wins, draws, losses, points);
        //highscoreList.getItems().add(HighscorePerson);
    }
}
