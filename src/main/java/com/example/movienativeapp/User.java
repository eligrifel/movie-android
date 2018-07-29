package com.example.movienativeapp;

/**
 *
 * this class hold user info
 */

public class User {
    private String user_id;
    private String user_name;
    private String first_name;
    private String last_name;
    private String credit_number;
    private String roll;
    private String credits;

    public User(String user_id, String user_name, String first_name, String last_name, String credit_number, String roll, String credits) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.credit_number = credit_number;
        this.roll = roll;
        this.credits = credits;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCredit_number() {
        return credit_number;
    }

    public void setCredit_number(String credit_number) {
        this.credit_number = credit_number;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }


}
