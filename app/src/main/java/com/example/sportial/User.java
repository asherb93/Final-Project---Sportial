package com.example.sportial;

import java.io.File;
import java.util.HashMap;


public class User {
    private final String userId;
    private int birthDay;
    private String birthMonth;
    private int birthYear;
    private final String firstName;
    private final String lastName;
    private final String city;
    private final String country;
    private final String gender;

    public User(String userId, String firstName, String lastName,int birthDay, String birthMonth, int birthYear, String gender, String city, String country) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
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