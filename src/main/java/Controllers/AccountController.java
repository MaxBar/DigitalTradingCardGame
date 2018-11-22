package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import main.Main2;

public class AccountController {
    String name;
    String email;
    String password;

    @FXML
    void handleSubmitButton() throws Exception{
        renderMenu();

}
    public void renderMenu() throws Exception {
        Pane game = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        Main2.primaryStage.setTitle("Candy Wars");
        Main2.primaryStage.setScene(new Scene(game, 1280, 720));
        Main2.primaryStage.show();
    }
}
