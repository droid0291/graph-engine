package com.pfizer.droidfactory.mygraph.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pfizer.droidfactory.mygraph.R;

public class GraphPeriodSelection extends AppCompatActivity implements View.OnClickListener {

    public static final String PERIOD_OF_GRAPH = "period_of_graph";

    public static final String GRAPH_COMPLETE = "graph_complete";

    public static final String GRAPH_LAST_SEVEN_DAYS = "graph_last_seven_days";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph_screen_size_selection);

        initUI();
    }

    private void initUI() {

        Button btnFullScreen = (Button) findViewById(R.id.btn_complete_graph);
        btnFullScreen.setOnClickListener(this);

        Button btnHalfScreen = (Button) findViewById(R.id.btn_last_seven_days_graph);
        btnHalfScreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_complete_graph:
                Intent intentComplete = new Intent(GraphPeriodSelection.this,
                        GraphTypeSelectionActivity.class);
                intentComplete.putExtra(PERIOD_OF_GRAPH, GRAPH_COMPLETE);
                startActivity(intentComplete);
                break;

            case R.id.btn_last_seven_days_graph:
                Intent intentSevenDays = new Intent(GraphPeriodSelection.this,
                        GraphTypeSelectionActivity.class);
                intentSevenDays.putExtra(PERIOD_OF_GRAPH, GRAPH_LAST_SEVEN_DAYS);
                startActivity(intentSevenDays);
                break;
        }
    }
}
