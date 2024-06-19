package com.example.sportial.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

public class User implements Serializable{
      String userId;
      int birthDay;
      String birthMonth;
      int birthYear;
      String firstName;
      String lastName;
      String city;
      String gender;
      String sportType;
    public User() {

    }

    public User(String firstName, String lastName,int birthDay, String birthMonth, int birthYear, String gender, String city) {
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
    public void setUserId(String userId) {
       this.userId = userId;
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
    public String getSportType() {
        return sportType;
    }
    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

}