package com.example.login;

public class User {
    public String fullName,age,email;
    boolean dogadaj;

    public User(String fullName, String age, String email,boolean dogadaj){
        this.fullName=fullName;
        this.age=age;
        this.email=email;
        this.dogadaj=dogadaj;
    }
}
