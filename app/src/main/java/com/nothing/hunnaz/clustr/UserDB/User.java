package com.nothing.hunnaz.clustr.UserDB;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public class User {
    private String name;
    private String pass;
    private String birthDate;

    public User(String name, String password, String dateOfBirth) {
        name = name;
        pass = password;
        birthDate = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return pass;
    }

    public String getDateOfBirth() {
        return birthDate;
    }


    // Taken from Account on Tic Tac Toe
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User account = (User) o;

        return name.equals(account.name) && pass.equals(account.pass) && birthDate.equals(account.birthDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode() + birthDate.hashCode();
        result = 31 * result + pass.hashCode();
        return result;

    }
}
