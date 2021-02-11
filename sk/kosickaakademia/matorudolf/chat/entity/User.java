package sk.kosickaakademia.matorudolf.chat.entity;

public class User {
    private int id;
    private String login;
    private String password;

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public User(int id, String login, String hashPassword) {
        this.id = id;
        this.login = login;
        this.password = password;

    }
}
