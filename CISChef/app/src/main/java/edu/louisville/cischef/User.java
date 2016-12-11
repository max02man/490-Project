package edu.louisville.cischef;

/**
 * Created by MAX MAN on 12/10/2016.
 */

public class User {

    private long id;
    private String username;
    private String email;

    public User() {};

    public User(long id, String username, String email) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "edu.louisville.cischef.User{" +
                "id=" + id +
                ", Username='" + username + '\'' +
                ", Email='" + email +
                '}';

    }
}