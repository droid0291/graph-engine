package com.pfizer.droidfactory.mygraph.model;

import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Shashi on 3/12/2017.
 */

public class GraphStepsModel implements Serializable {

    public static int maxValue = 0;

    private Date date;

    private int stepsCount;

    public GraphStepsModel() {}

    protected GraphStepsModel(Parcel in) {
        stepsCount = in.readInt();
    }

    /*public static final Creator<GraphStepsModel> CREATOR = new Creator<GraphStepsModel>() {
        @Override
        public GraphStepsModel createFromParcel(Parcel in) {
            return new GraphStepsModel(in);
        }

        @Override
        public GraphStepsModel[] newArray(int size) {
            return new GraphStepsModel[size];
        }
    };*/

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(int stepsCount) {
        this.stepsCount = stepsCount;
    }


    public static ArrayList<GraphStepsModel> generateStepsData() {

        ArrayList<GraphStepsModel> arrayListSteps = new ArrayList<>();

        for (int i = 0; i < 500; i++) {

            GraphStepsModel stepsModel = new GraphStepsModel();

            stepsModel.setDate(getStepDate(i));

            int randomNumber = new Random().nextInt(10000);

            if (randomNumber > maxValue) {
                maxValue = randomNumber;
            }

            stepsModel.setStepsCount(new Random().nextInt(10000));

            arrayListSteps.add(stepsModel);
        }

        return arrayListSteps;
    }

    private static Date getStepDate(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, -i);
        return calendar.getTime();
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date.getTime());
        dest.writeInt(stepsCount);
    }*/
}
