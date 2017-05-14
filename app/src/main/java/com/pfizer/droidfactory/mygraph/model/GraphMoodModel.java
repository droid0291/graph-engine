package com.pfizer.droidfactory.mygraph.model;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by User on 3/12/2017.
 */

public class GraphMoodModel implements Serializable {

    public static int maxValue = 13;

    private Date date;

    private int moodLevel;

    public Date getDate() {
        return date;
    }

    private void setDate(Date date) {
        this.date = date;
    }

    public int getMoodLevel() {
        return moodLevel;
    }

    private void setMoodLevel(int moodLevel) {
        this.moodLevel = moodLevel;
    }

    public static ArrayList<GraphMoodModel> getGraphMoodData(Context context) {

        ArrayList<GraphMoodModel> graphMoodModel = new ArrayList<>();

        for (int i = 0; i < 500; i++) {

            GraphMoodModel moodModel = new GraphMoodModel();

            moodModel.setDate(getMoodDate(i));

            moodModel.setMoodLevel(new Random().nextInt(13));

            graphMoodModel.add(moodModel);
        }

        return graphMoodModel;
    }

    private static Date getMoodDate(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, -i);
        return calendar.getTime();
    }
}
