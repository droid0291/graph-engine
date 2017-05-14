package com.pfizer.droidfactory.mygraph.model;

import java.util.Date;

/**
 * Created by User on 3/12/2017.
 */

public class GraphSleepModel {

    private Date date;

    private int sleepingHour;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSleepingHour() {
        return sleepingHour;
    }

    public void setSleepingHour(int sleepingHour) {
        this.sleepingHour = sleepingHour;
    }
}
