package dto;

import java.util.Random;

public class User {
    private String email;
    private String password;

    public User(){
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void generateRandomUser(){
        Random random = new Random();
        this.email = "User" + random.nextInt(1000,9999) + "@example.com";
        this.password = "Password@" + random.nextInt(1000,9999);


    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
