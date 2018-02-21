package com.nothing.hunnaz.clustr;

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
        if(yearVal < 100){
            yearVal += 1900;
        }
        return yearVal;
    }

    private boolean validateDate(){
        boolean monthValid = (this.month > 0 && this.month < 13);
        boolean dayValid = (this.day > 0 && this.day < 32);
        boolean yearValid = (this.year > 1900 && this.year < 2018);

        return monthValid && dayValid && yearValid;
    }

    public String toString(){
        String returnVal = "";
        if(this.validDate){
            returnVal = this.month + "/" + this.day + "/" + this.year;
        }
        return returnVal;
    }

    public boolean confirmDate() {
        return this.validDate;
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

        this.validDate = validateDate();
    }
}
