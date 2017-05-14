package com.pfizer.droidfactory.mygraph;

import com.pfizer.droidfactory.mygraph.model.GraphMoodModel;
import com.pfizer.droidfactory.mygraph.model.GraphStepsModel;
import com.pfizer.droidfactory.mygraph.view.GraphTypeSelectionActivity;
import com.pfizer.droidfactory.mygraph.view.MainActivity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by User on 3/12/2017.
 */

public class GraphUtils {

    public static ArrayList<Integer> getUniformDistribution(int low, int high, int range) {

        ArrayList<Integer> yAxisDataRange = new ArrayList<>();

        int slot = (high - low) / range;

        for (int i = 0; i < range; i++) {

            yAxisDataRange.add(low + (slot * (i + 1)));
        }

        Collections.reverse(yAxisDataRange);

        return yAxisDataRange;
    }

    public static int getYAxisLabelCount(String graphType) {

            switch (graphType) {

            case GraphTypeSelectionActivity.TYPE_STEPS:
                return MainActivity.Y_AXIS_LABEL_COUNT_STEPS;

            case GraphTypeSelectionActivity.TYPE_MOOD:
                return MainActivity.Y_AXIS_LABEL_COUNT_MOOD;

            default:
                return MainActivity.Y_AXIS_LABEL_COUNT_STEPS;
        }
    }

    public static int getMaxValue(String graphType) {

        switch (graphType) {

            case GraphTypeSelectionActivity.TYPE_STEPS:
                return GraphStepsModel.maxValue;

            case GraphTypeSelectionActivity.TYPE_MOOD:
                return GraphMoodModel.maxValue;

            default:
                return GraphStepsModel.maxValue;
        }
    }
}
