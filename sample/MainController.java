package sample;

import javafx.scene.control.Label;
import sk.kosickaakademia.matorudolf.chat.entity.User;

import java.awt.event.MouseEvent;


public class MainController {
    public Label lbl_login;
    private User user;

    public MainController() {
        System.out.println("Konstruktor");
    }
    public void setUser(User user){
        this.user=user;
    }
    public void logout(MouseEvent mouseEvent){

    }
    public void initLoginName() {
        lbl_login.setText(user.getLogin());
    }
}
