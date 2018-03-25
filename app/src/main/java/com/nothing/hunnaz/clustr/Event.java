package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public class Event {
    private String title;
    private String address;
    private int capacity;
    private String date;
    private String description;
    private double cost;
    private int age;
    private String creator;
    private int currAttending;
    private String key;
    private Time time;
    // TODO: implement pictureIDField and guestListField

    public Event(String title, String address, int capacity, String date, String description, double cost, int age, String creatorID, int currAttendance, Time t) {
        this.title = title;
        this.address = address;
        this.capacity = capacity;
        this.date = date;
        this.description = description;
        this.cost = cost;
        this.age = age;
        this.creator = creatorID;
        this.currAttending = currAttendance;
        this.key = "";
        this.time = t;
    }
    
    public Event() {}

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getKey() {
        return this.key;
    }

    public void setKey(String k) {
        this.key = k;
    }

    public Time getTime() {
        return this.time;
    }

    public void setTime(Time k) {
        this.time = k;
    }

    public Location getLocation(Context context) {
        Location loc = new Location(LocationManager.GPS_PROVIDER);
        Geocoder coder = new Geocoder(context);
        List<Address> addresses;
        try {
            // May throw an IOException
            addresses = coder.getFromLocationName(address, 5);
            if (address != null) {
                Address location = addresses.get(0);
                loc.setLatitude(location.getLatitude());
                loc.setLongitude(location.getLongitude());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return loc;
    }

    @Override
    public String toString() {
        return "Title: " + this.title +
                "\nLocation: " + this.address +
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
                .append(address, event.address)
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
                .append(address)
                .append(capacity)
                .append(date)
                .append(description)
                .append(cost)
                .append(age)
                .append(creator)
                .toHashCode();
    }
}
