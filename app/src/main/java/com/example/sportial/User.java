package com.example.sportial;

import java.io.File;
import java.util.HashMap;


public class User {
    private final String userId;
    private final int birthDay;
    private final String birthMonth;
    private final int birthYear;
    private final String firstName;
    private final String lastName;
    private final String city;
    private final String country;
    private final String gender;

    public User(String userId, String firstName, String lastName,int birthDay, String birthMonth, int birthYear, String gender, String city, String country) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.city = city;
        this.country = country;
        this.gender = gender;
    }
    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
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

    public String getCountry() {
        return country;
    }

    public String getGender() {
        return gender;
    }


}