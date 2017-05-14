package com.pfizer.droidfactory.mygraph.model;

import java.util.Date;

/**
 * Created by User on 3/12/2017.
 */

public class GraphPainModel {

    private Date date;

    private int painIntensity;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPainIntensity() {
        return painIntensity;
    }

    public void setPainIntensity(int painIntensity) {
        this.painIntensity = painIntensity;
    }
}
