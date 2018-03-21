package com.nothing.hunnaz.clustr.EventDB;

import org.apache.commons.lang3.builder.*;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public class Event {
    private String title;
    private String location;
    private int capacity;
    private String date;
    private String description;
    private double cost;
    private int age;
    private String creator;
    private int currAttending;
    // TODO: implement pictureIDField and guestListField

    public Event(String title, String location, int capacity, String date, String description, double cost, int age, String creatorID, int currAttendance) {
        this.title = title;
        this.location = location;
        this.capacity = capacity;
        this.date = date;
        this.description = description;
        this.cost = cost;
        this.age = age;
        this.creator = creatorID;
        this.currAttending = currAttendance;
    }
    
    public Event() {}

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCreatorId() {
        return creator;
    }

    public void setCreatorId(String creator) {
        this.creator = creator;
    }

    public int getNumCurrentAttending() {
        return currAttending;
    }

    public void setNumCurrentAttending(int num) {
        this.currAttending = num;
    }

    @Override
    public String toString() {
        return "Title: " + this.title +
                "\nLocation: " + this.location +
                "\nCapacity: " + this.capacity +
                "\nDate: " + this.date +
                "\nDescription: " + this.description +
                "\nCost: " + this.cost +
                "\nAge: " + this.age +
                "\nCreator: " + this.creator;
    }

    // Taken from https://www.mkyong.com/java/java-how-to-overrides-equals-and-hashcode/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) {
            return false;
        }

        Event event = (Event) o;

        return new EqualsBuilder()
                .append(title, event.title)
                .append(location, event.location)
                .append(capacity, event.capacity)
                .append(date, event.date)
                .append(description, event.description)
                .append(cost, event.cost)
                .append(age, event.age)
                .append(creator, event.creator)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(location)
                .append(capacity)
                .append(date)
                .append(description)
                .append(cost)
                .append(age)
                .append(creator)
                .toHashCode();
    }
}
