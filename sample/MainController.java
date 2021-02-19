package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import sk.kosickaakademia.matorudolf.chat.database.Database;
import sk.kosickaakademia.matorudolf.chat.entity.Message;
import sk.kosickaakademia.matorudolf.chat.entity.User;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;


public class MainController {
    public Label lbl_login;
    private User user;
    public TextField txt_newmessage;
    public TextField txt_receiver;
    public ListView lbx_messages;



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
    public void sendm (ActionEvent actionEvent){
        String receiver = txt_receiver.getText().trim();
        String newMessage = txt_newmessage.getText().trim();
        if(receiver.length()<1 || newMessage.length()<1)
            return;
        boolean result = new Database() . sendMessage(user.getId(),receiver,newMessage);
        txt_receiver.setText("");
        txt_newmessage.setText("");

    }
    public void refreshm (ActionEvent actionEvent){

        List<Message> list = new Database().getMyMessages(user.getLogin());
        if(list.isEmpty()){
            return;
        }
        for(Message m:list){
            String sender = m.getFrom();
            Date dt = m.getDt();
            String text = m.getText();
            lbx_messages.getItems().add(0, sender + " " + dt);
            lbx_messages.getItems().add(1, " > " + text);


        }
    }
}
