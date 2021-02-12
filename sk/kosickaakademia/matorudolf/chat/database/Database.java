package sk.kosickaakademia.matorudolf.chat.database;

import sk.kosickaakademia.matorudolf.chat.entity.Message;
import sk.kosickaakademia.matorudolf.chat.entity.User;
import sk.kosickaakademia.matorudolf.chat.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String username = "mysqluser";
    private String password = "Kosice2021!";
    private final String insertNewUser = "INSERT INTO user(login,password) VALUES(?,?)";
    private final String loginUser = "Select * FROM user WHERE login LIKE ? AND password LIKE ? ";
    private final String newMessage = "INSERT INTO message (frto, to, text) VALUES ( ?,?,?) ";

    private Connection getConnection() throws  SQLException, ClassNotFoundException {
        Class.forName( "com.mysql.cj.jdbc.Driver" );
        Connection conn = DriverManager.getConnection (url, username, password );
        return conn;
        }
    public void test () {
        try{
            Connection con = getConnection ();
            if(con== null) {
                System.out.println("Chyba!");
            }
            else {
                System.out.println(" OK ");
                con.close();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public boolean insertNewUser(String login, String password){
        if(login== null || login.equals("") || password== null || password.length() <6 )
            return false;
        String hashpassword = new Util().getMd5(password);
        try{
            Connection con =  getConnection();
            if(con == null) {
                System.out.println("Chyba !");
                return false;

            }
            PreparedStatement ps = con.prepareStatement( insertNewUser );
            ps.setString(1, login);
            ps.setString(2,hashpassword);
            int result = ps.executeUpdate();
            con.close();
            if (result ==0)
                return false;
            else {
                System.out.println(" User " + login + " vlozeny! ");
                return true;

            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
             }
        return true;

    }
    public User loginUser (String login, String password) {
        if (login == null|| login.equals("")|| password == null || password.length() <6)
            return null;
        String hashPassword = new Util().getMd5(password);
        try{
            Connection con = getConnection();
            if (con ==null) {
                System.out.println("Chyba pripojenia! ");
                return null;

            }
            PreparedStatement ps = con.prepareStatement(loginUser);
            ps.setString(1, login);
            ps.setString(2,hashPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println( "Login OK! ");
                int id = rs.getInt ("id");
                User user = new User (id, login, hashPassword );
                con.close();
                return user;
                }else{
                con.close();
                System.out.println("Nespravne udaje! ");
                return null;

            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
    public boolean changePassword (String login , String oldPassword, String newPassword){
        if (login == null  || login.equals("")  || oldPassword == null || oldPassword.length() <6 || newPassword == null || newPassword.length() <6 )
                    return false;
        try{
            Connection con = getConnection();
            if (con ==null) {
                System.out.println("Chyba pripojenia! ");
                return false;

            PreparedStatement ps = con.prepareStatement( changePassword );
                ps.setString(1, newPassword );
                ps.setString(2, login);
                ps.setString(1, oldPassword);
                int result = ps.executeUpdate();
                con.close( );

    }} catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean sendMessage(int from, String toUser,String text){
        if(text == null || text.equals (""))
            return false;
        int to = getUserId(toUser);
        if(to == -1)
            return false;
        try {
            Connection con = getConnection();
            if (con == null) {
                System.out.println("Chyba spojenia! ");
                return false;
            }
            PreparedStatement ps = con.prepareStatement(newMessage);
            ps.setInt(1,from);
            ps.setInt(2,to);
            ps.setString(3,text);
            int result = ps.executeUpdate();
            con.close();
            if(result<1){
                System.out.println("Správa sa neodoslala!");
                return false;
                } else {
                System.out.println("Správa sa odoslala!");
                return true;
               }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
     }
    public int getUserId(String login){

        int id = -1;
        if (login == null || login.equals ("" ))
           return id;

        try {
            Connection con = getConnection();
                if (con == null) {
                    System.out.println("Chyba spojenia! ");
                    return id;

                    PreparedStatement ps = con.prepareStatement(getUserId);
                    ps.setInt(1,login);
                    int result = ps.executeUpdate();
                    con.close();
        }
    } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMyMessages(String login){
        List<Message> ls= new ArrayList<>();
        if (login == null || login.equals ("" ))
            return ls;
        try {
            Connection con = getConnection();
            if (con == null) {
                System.out.println("Chyba spojenia! ");
                return ls;

                PreparedStatement ps = con.prepareStatement(getMyMessages);
                ps.setInt(1,login);
                int result = ps.executeUpdate();
                con.close();
        }
        return null;
    } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteAllMyMessages(String login) {
        if (login == null || login.equals(""))
            return false; 

        try {
            Connection con = getConnection();
            if (con == null) {
                System.out.println("Chyba spojenia! ");
                return false;

                PreparedStatement ps = con.prepareStatement(deleteAllMyMessages);
                ps.setInt(1,login);
                int result = ps.executeUpdate();
                con.close();


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }return true;
    }
}
