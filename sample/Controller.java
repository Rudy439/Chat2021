package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.kosickaakademia.matorudolf.chat.database.Database;
import sk.kosickaakademia.matorudolf.chat.entity.User;

import java.io.IOException;


public class Controller {

    @FXML
    private TextField txt_login;
    public PasswordField txt_password;
    public Label lbl_error;
    public Button btn_login;

    public void btn_click(ActionEvent actionEvent) {
        System.out.println("Funguje");
        String login = txt_login.getText().trim();
        String password = txt_password.getText().trim();
        if(login.length()>0 && password.length()>0){
            Database db = new Database();
            User user = db.loginUser(login,password);
            if(user==null){
                lbl_error.setVisible(true);}
            else{
                System.out.println("Pripojený!");
                openMainForm(user);

            }

        }
    }
    private void openMainForm(User user){
        try{
            FXMLLoader root = new FXMLLoader();
            root.setLocation(getClass().getResource("main.fxml"));
            Stage stg = new Stage();
            stg.setTitle("Chat 2021 application");
            stg.setScene(new Scene(root.load(), 800,600));
            stg.show();
            MainController mcr = root.getController();
            mcr.setUser(user);
            mcr.initLoginName();
            btn_login.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
