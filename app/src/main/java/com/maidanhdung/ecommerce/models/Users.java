package com.maidanhdung.ecommerce.models;

public class Users {
    String LastName, FirstName, Password, Avatar;
    int PhoneNumber;
    public Users(){}

    public Users(String lastName, String firstName, String password, String avatar, int phoneNumber) {
        LastName = lastName;
        FirstName = firstName;
        Password = password;
        Avatar = avatar;
        PhoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
