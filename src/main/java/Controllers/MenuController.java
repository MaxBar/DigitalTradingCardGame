package Controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import main.Main;

public class MenuController {
    private Main main;
    
    private Stage stage = new Stage();
    
    @FXML
    void handleStartButton() throws Exception {
        // Todo: See if there is a way to change scene instead of creating a new stage
        main = new Main();
        //main.renderGame(stage);
        System.out.println("Button was clicked");
    }
    
    @FXML
    void handleQuitButton(){
        Platform.exit();
        
    }
}