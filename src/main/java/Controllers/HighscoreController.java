package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import player.Highscore;
import repository.QueryHandler;

import java.util.ArrayList;

public class HighscoreController {
    @FXML
    TableView highscoreList;
    @FXML
    private TableColumn name;
    
    @FXML
    private TableColumn wins;
    
    @FXML
    private TableColumn draws;
    
    @FXML
    private TableColumn losses;
    
    @FXML
    private TableColumn points;
    
    @FXML
    public void initialize() {
        QueryHandler queryHandler = new QueryHandler();
        
        name.setCellValueFactory(new PropertyValueFactory<Highscore, String>("name"));
        wins.setCellValueFactory(new PropertyValueFactory<Highscore, Integer>("wins"));
        draws.setCellValueFactory(new PropertyValueFactory<Highscore, Integer>("draws"));
        losses.setCellValueFactory(new PropertyValueFactory<Highscore, Integer>("losses"));
        points.setCellValueFactory(new PropertyValueFactory<Highscore, Integer>("points"));
    
        ArrayList<Highscore> highscores = new ArrayList<>(queryHandler.highscorePlayers());
    
        for(Highscore highscore : highscores) {
            highscoreList.getItems().add(highscore);
        }
    }
}
