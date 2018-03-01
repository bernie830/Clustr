package com.nothing.hunnaz.clustr.UserDB;

import org.apache.commons.lang3.builder.*;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public class User {
    private String birthday;
    private String email;
    private String password;
    private String username;
    // TODO: implement created and attending

    public User(String birthday, String email, String password, String username) {
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Username: " + this.username +
                "\nBirthday: " + this.birthday +
                "\nEmail: " + this.email +
                "\nPassword: " + this.password;
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
                .append(password, event.password)
                .append(username, event.username)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(birthday)
                .append(email)
                .append(password)
                .append(username)
                .toHashCode();
    }
}
