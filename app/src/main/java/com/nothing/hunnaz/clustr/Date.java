package com.nothing.hunnaz.clustr;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by hunterbernhardt on 2/21/18.
 */

public class Date {

    private int month;
    private int day;
    private int year;
    private boolean validDate = false;

    private static int getMonthNum(String month) {
        int monthVal = 0;
        // If possible, convert it to an integer
        for(int i = 0; i < month.length(); i++){
            if(Character.isDigit(month.charAt(i)) && monthVal >= 0){
                monthVal = (monthVal * 10) + Character.digit(month.charAt(i), 10);
            } else {
                monthVal = -1;
            }
        }
        return monthVal;
    }

    private static int getDayNum(String day) {
        int dayVal = 0;
        // If possible, convert it to an integer
        for(int i = 0; i < day.length(); i++){
            if(Character.isDigit(day.charAt(i)) && dayVal >= 0){
                dayVal = (dayVal * 10) + Character.digit(day.charAt(i), 10);
            } else {
                dayVal = -1;
            }
        }
        return dayVal;
    }

    private static int getYearNum(String year) {
        int yearVal = 0;
        // If possible, convert it to an integer
        for(int i = 0; i < year.length(); i++){
            if(Character.isDigit(year.charAt(i)) && yearVal >= 0){
                yearVal = (yearVal * 10) + Character.digit(year.charAt(i), 10);
            } else {
                yearVal = -1;
            }
        }
        if(yearVal < 25){ // TODO - Arbitrary date
            yearVal += 2000;
        } else if(yearVal < 100){
            yearVal += 1900;
        }
        return yearVal;
    }

    public int getYear(){
        return this.year;
    }

    public int getMonth(){
        return this.month;
    }

    public int getDay(){
        return this.day;
    }

    private boolean dateAfter(Date day){
        boolean returnVal = false;
        if(day.getYear() <= this.year){
            returnVal = true;
        } else if(day.getYear() == this.year && day.getMonth() <= this.month){
            returnVal = true;
        } else if(day.getYear() == this.year && day.getMonth() == this.month && day.getDay() <= this.day){
            returnVal = true;
        }
        return returnVal;
    }

    private boolean dateBefore(Date day){
        boolean returnVal = false;
        if(day.getYear() >= this.year){
            returnVal = true;
        } else if(day.getYear() == this.year && day.getMonth() >= this.month){
            returnVal = true;
        } else if(day.getYear() == this.year && day.getMonth() == this.month && day.getDay() >= this.day){
            returnVal = true;
        }
        return returnVal;
    }

    public boolean notYetOccurred(Date day){
        Date curr = getCurrentDate();
        return dateAfter(curr);
    }

    private Date getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date date = new java.util.Date();
        Date today = new Date(dateFormat.format(date));
        return today;
    }

    private boolean validateDate(boolean forEvent){
        boolean monthValid = (this.month > 0 && this.month < 13);
        boolean dayValid = (this.day > 0 && this.day < 32);
        boolean value = monthValid && dayValid;
        Date today = getCurrentDate();

        if(forEvent) {
            if (!dateAfter(today)) {
                value = false;
            }
        } else {
            if (!dateBefore(today)) {
                value = false;
            }
        }

        return value;
    }

    public boolean isOlderThan(int age){
        Date day = getCurrentDate();
        int newYear = day.getYear() - age;// TODO - kernel purity??
        Date dayToCompare = new Date(Integer.toString(day.getMonth()), Integer.toString(day.getDay()), Integer.toString(newYear));
        return dateBefore(dayToCompare);
    }

    public String toString(){
        String returnVal = this.month + "/" + this.day + "/" + this.year;
        return returnVal;
    }

    public boolean confirmDate(boolean forEvent) {
        return validateDate(forEvent);
    }

    public Date (String monthStr, String dayStr, String yearStr){
        this.month = getMonthNum(monthStr);
        this.day = getDayNum(dayStr);
        this.year = getYearNum(yearStr);
    }

    public Date(String date){
        String monthStr = "";
        String dayStr = "";
        String yearStr = "";
        int slash = date.indexOf("/");
        if(slash > -1) {
            monthStr = date.substring(0, slash);
            String rest = date.substring(slash + 1);
            slash = rest.indexOf("/");
            if(slash > -1) {
                dayStr = rest.substring(0, slash);
                yearStr = rest.substring(slash + 1);
            } else {
                monthStr = "";
                dayStr = "";
                yearStr = "";
            }
        } else {
            monthStr = "";
            dayStr = "";
            yearStr = "";
        }

        this.month = getMonthNum(monthStr);
        this.day = getDayNum(dayStr);
        this.year = getYearNum(yearStr);

    }
}
