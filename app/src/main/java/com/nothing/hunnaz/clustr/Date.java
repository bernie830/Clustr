package com.nothing.hunnaz.clustr;

/**
 * Created by hunterbernhardt on 2/21/18.
 */

public class Date {

    private String month;
    private String day;
    private String year;

    private boolean confirmMonth() {
        int monthVal = 0;
        // If possible, convert it to an integer
        for(int i = 0; i < this.month.length(); i++){
            if(Character.isDigit(this.month.charAt(i)) && monthVal >= 0){
                monthVal = (monthVal * 10) + Character.digit(this.month.charAt(i), 10);
            } else {
                monthVal = -1;
            }
        }
        return (monthVal > 0 && monthVal < 13);
    }

    private boolean confirmDay() {
        int dayVal = 0;
        // If possible, convert it to an integer
        for(int i = 0; i < this.day.length(); i++){
            if(Character.isDigit(this.day.charAt(i)) && dayVal >= 0){
                dayVal = (dayVal * 10) + Character.digit(this.day.charAt(i), 10);
            } else {
                dayVal = -1;
            }
        }
        return (dayVal > 0 && dayVal < 32);
    }

    private boolean confirmYear() {
        int yearVal = 0;
        // If possible, convert it to an integer
        for(int i = 0; i < this.year.length(); i++){
            if(Character.isDigit(this.year.charAt(i)) && yearVal >= 0){
                yearVal = (yearVal * 10) + Character.digit(this.year.charAt(i), 10);
            } else {
                yearVal = -1;
            }
        }
        if(yearVal < 100){
            yearVal += 1900;
        }
        return (yearVal > 1900 && yearVal < 2018);
    }

    public boolean confirmDate() {
        boolean returnVal = confirmMonth() && confirmDay() && confirmYear();

        return returnVal;
    }

    public Date (String monthStr, String dayStr, String yearStr){
        month = monthStr;
        day = dayStr;
        year = yearStr;
    }
}
