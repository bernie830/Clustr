package com.nothing.hunnaz.clustr.EventDB;

/**
 * Created by hunterbernhardt on 2/9/18.
 */
public class Event {
    private String titleField;
    private String locationField;
    private int capacityField;
    private String dateField;
    private String descriptionField;
    private double costField;
    private int ageField;
    private String creatorField;

    public Event(String title, String location, int capacity, String date, String description, double cost, int age, String creator) {
        titleField = title;
        locationField = location;
        capacityField = capacity;
        dateField = date;
        descriptionField = description;
        costField = cost;
        ageField = age;
        creatorField = title;
    }

    public String getTitle() {
        return titleField;
    }

    public String getLocation() {
        return locationField;
    }

    public int getCapacity() {
        return capacityField;
    }

    public String getDate() { return dateField;}

    public String getDescription() { return descriptionField;}

    public double getCost() {
        return costField;
    }

    public int getAge() {
        return ageField;
    }

    public String getCreator() {
        return creatorField;
    }


    // Taken from Account on Tic Tac Toe
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return titleField.equals(event.titleField) && locationField.equals(event.locationField) && dateField.equals(event.dateField);
    }

    @Override
    public int hashCode() {
        int result = titleField.hashCode() + dateField.hashCode();
        result = 31 * result + descriptionField.hashCode();
        return result;

    }
}
