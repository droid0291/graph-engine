package com.pfizer.droidfactory.mygraph.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.pfizer.droidfactory.mygraph.R;
import com.pfizer.droidfactory.mygraph.model.GraphMoodModel;
import com.pfizer.droidfactory.mygraph.model.GraphStepsModel;

import java.util.ArrayList;

/**
 * Created by Shashi on 3/11/2017.
 */

public class GraphTypeSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG = "MyGraph";

    public static final String GRAPH_DATA = "steps_data";

    public static final String GRAPH_TYPE = "graph_type";

    public static final String TYPE_STEPS = "type_steps";

    public static final String TYPE_MOOD = "type_mood";

    String periodOfGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph_type_selection);

        initUI();

        getBundleData();
    }

    private void initUI() {

        Button btnStepsGraph = (Button) findViewById(R.id.btn_steps_graph);
        btnStepsGraph.setOnClickListener(this);

        Button btnSleepGraph = (Button) findViewById(R.id.btn_sleep_graph);
        btnSleepGraph.setOnClickListener(this);

        Button btnMoodGraph = (Button) findViewById(R.id.btn_mood_graph);
        btnMoodGraph.setOnClickListener(this);

        Button btnPainGraph = (Button) findViewById(R.id.btn_pain_graph);
        btnPainGraph.setOnClickListener(this);
    }

    private void getBundleData() {

//        periodOfGraph = getIntent().getExtras().getString(GraphPeriodSelection.PERIOD_OF_GRAPH);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_steps_graph:
                ArrayList<GraphStepsModel> arrayList = GraphStepsModel.generateStepsData();
                Log.d(LOG, "-----size of step arrayList = " + arrayList.size());
                Intent intentStepsGraph = new Intent(this, MainActivity.class);
                intentStepsGraph.putExtra(GRAPH_TYPE, TYPE_STEPS);
                intentStepsGraph.putExtra(GRAPH_DATA, arrayList);
                startActivity(intentStepsGraph);
                break;

            case R.id.btn_sleep_graph:

                break;

            case R.id.btn_mood_graph:
                ArrayList<GraphMoodModel> arrayListMood = GraphMoodModel.getGraphMoodData(this);
                Log.d(LOG, "-----size of step arrayList = " + arrayListMood.size());
                Intent intentMoodGraph = new Intent(this, MainActivity.class);
                intentMoodGraph.putExtra(GRAPH_TYPE, TYPE_MOOD);
                intentMoodGraph.putExtra(GRAPH_DATA, arrayListMood);
                startActivity(intentMoodGraph);
                break;

            case R.id.btn_pain_graph:
                break;
        }
    }
}
