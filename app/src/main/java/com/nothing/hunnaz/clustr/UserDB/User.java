package com.nothing.hunnaz.clustr.UserDB;

import org.apache.commons.lang3.builder.*;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public class User {
    private String birthday;
    private String email;
    private String username;
    private String accountID;
    // TODO: implement created and attending

    public User(String birthday, String email,  String username, String accountID) {
        this.birthday = birthday;
        this.email = email;
        this.username = username;
        this.accountID = accountID;
    }

    public User() {}

    // Getters and Setters
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    @Override
    public String toString() {
        return "Username: " + this.username +
                "\nEmail: " + this.email +
                "\nBirthday: " + this.birthday +
                "\nAccount ID: " + this.accountID;
    }

    // Taken from https://www.mkyong.com/java/java-how-to-overrides-equals-and-hashcode/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) {
            return false;
        }

        User event = (User) o;

        return new EqualsBuilder()
                .append(birthday, event.birthday)
                .append(email, event.email)
                .append(username, event.username)
                .append(accountID, event.accountID)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(birthday)
                .append(email)
                .append(username)
                .append(accountID)
                .toHashCode();
    }
}
