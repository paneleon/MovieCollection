package com.example.moviecollection.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class User {

    String uuid;
    String firstName;
    String lastName;
    String username;
    String email;
    int age;
    String gender;

//    public User(String authUuid, String username) {
//        this.authUuid = authUuid;
//        this.username = username;
//    }

    public User(String authUuid, String firstName, String lastName, String username, String email, int age, String gender) {
        this.uuid = authUuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}