package com.nothing.hunnaz.clustr.MiddleDB;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public class UserToEvent {
    private int userID;
    private int eventID;

    public UserToEvent(int user_id, int event_id) {
        userID = user_id;
        eventID = event_id;
    }

    public int getUserID() {
        return userID;
    }

    public int getEventID() {
        return eventID;
    }



    // Taken from Account on Tic Tac Toe
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserToEvent account = (UserToEvent) o;

        return (userID == account.userID) && (userID == account.userID);
    }

    @Override
    public int hashCode() {
        int result = userID;
        result = 31 * result + eventID;
        return result;
    }
}
