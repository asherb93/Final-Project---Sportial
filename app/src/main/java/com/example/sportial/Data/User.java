package com.example.sportial.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class User {
      String userId;
      int birthDay;
      String birthMonth;
      int birthYear;
      String firstName;
      String lastName;
      String city;
      String gender;
    public User() {

    }
    public User(String userId, String firstName, String lastName,int birthDay, String birthMonth, int birthYear, String gender, String city) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.city = city;
        this.gender = gender;
    }


    public String getUserId() {
        return userId;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getBirthDay() {
        return birthDay;
    }
    public String getBirthMonth() {
        return birthMonth;
    }
    public int getBirthYear() {
        return birthYear;
    }

    public String getCity() {
        return city;
    }


    public String getGender() {
        return gender;
    }

}