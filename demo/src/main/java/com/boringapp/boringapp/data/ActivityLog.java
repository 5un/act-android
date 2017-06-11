package com.boringapp.boringapp.data;

/**
 * Created by Pakapon on 6/11/2017 AD.
 */

public class ActivityLog {
    public String dateString;
    public String description;
    public int point;

    public ActivityLog(String dateString, String description, int point) {
        this.dateString = dateString;
        this.description = description;
        this.point = point;
    }
}
