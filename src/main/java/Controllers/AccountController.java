package Controllers;

import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.Main2;



public class AccountController {
    String name;
    String email;
    String password;

    @FXML
    private TextField accountName;
    @FXML
    private TextField accountEmail;
    @FXML
    private TextField accountPassword;

    @FXML
    void handleSubmitButton() throws Exception{
        saveEnteredDetails();
        renderMenu();

}
    public void renderMenu() throws Exception {
        Pane game = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        Main2.primaryStage.setTitle("Candy Wars");
        Main2.primaryStage.setScene(new Scene(game, 1280, 720));
        Main2.primaryStage.show();
    }
    void saveEnteredDetails(){
        name = accountName.getText();
        email = accountEmail.getText();
        password = accountPassword.getText();
        System.out.println(name);
    }

}
