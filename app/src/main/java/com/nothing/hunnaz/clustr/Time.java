package com.nothing.hunnaz.clustr;

/**
 * Created by hunterbernhardt on 2/21/18.
 */

public class Time {

    private int hour;
    private int minute;
    private boolean isInAM;

    public Time(){
        this.hour = 12;
        this.minute = 1;
        this.isInAM = true;
    }

    public Time(int hour, int minute){
        if(hour > 12){
            this.isInAM = false;
            this.hour = hour - 12;
        } else {
            this.hour = hour;
            this.isInAM = true;
        }
        this.minute = minute;
    }

    public int get24Hour(){
        int retVal = this.hour;
        if(!this.isInAM){
            retVal = this.hour + 12;
        }

        return retVal;
    }

    public int getHour(){
        return this.hour;
    }

    public int getMinute(){
        return this.minute;
    }

    public boolean isInAM(){
        return this.isInAM;
    }

    public String toString(){
        String retVal = "";
        String minute = "" + this.minute;
        if(this.minute < 10){
            minute = "0" + this.minute;
        }
        if(this.isInAM){
            retVal = "" + this.hour + ":" + minute + " AM";
        } else {
            retVal = "" + this.hour + ":" + minute + " PM";
        }
        return retVal;
    }
}
