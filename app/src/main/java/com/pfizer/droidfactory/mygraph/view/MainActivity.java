package com.pfizer.droidfactory.mygraph.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pfizer.droidfactory.mygraph.DividerDecoration;
import com.pfizer.droidfactory.mygraph.GraphUtils;
import com.pfizer.droidfactory.mygraph.R;
import com.pfizer.droidfactory.mygraph.adapter.GraphMonthAdapter;
import com.pfizer.droidfactory.mygraph.adapter.GraphViewAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements ViewTreeObserver.OnGlobalLayoutListener {

    ///////////////////////////////////////////////////////////////////////////
    // UI and class members
    ///////////////////////////////////////////////////////////////////////////

    RecyclerView mRecyclerViewGraph;

    GraphViewAdapter mGraphViewAdapter;

    RecyclerView mRecyclerViewMonth;

    GraphMonthAdapter mMonthViewAdapter;

    View viewEmptyArea;

    ArrayList<Integer> horizontalList;

    ArrayList<Integer> arrayListYAxisLabelPosition;

    LinearLayout linearLayoutYAxisLabel;

    // height of the graph
    int heightOfGraphArea;

    // top position on the screen
    float startPositionOfGraphView;

    // bottom position on the screen
    float endPositionOfGraph;

    // IMPORTANT: Will play major role in calculation of Y - Axis label position
    int heightOfLabelTextInPixels;

    // The label count will be one more than the actual label count on Y Axis
    public static final int Y_AXIS_LABEL_COUNT_STEPS = 10;

    public static final int Y_AXIS_LABEL_COUNT_MOOD = 13;

    public static final int Y_AXIS_LABEL_TOP_OFFSET = 40;

    private static final int Y_AXIS_LABEL_BOTTOM_OFFSET = 20;

    private int equalInterval;

    private String graphType;

    private ArrayList<?> graphData;

    ///////////////////////////////////////////////////////////////////////////
    // Overridden methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigger_graph);
//        setContentView(R.layout.activity_smaller_graph);

        initUI();

        getBundleData();

//        prepareDummyDataForGraph();
    }

    @Override
    public void onGlobalLayout() {

        mRecyclerViewGraph.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//        linearLayoutYAxisLabel.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        // get the height on the screen
        heightOfGraphArea = linearLayoutYAxisLabel.getHeight();

        int[] coordinates = {0, 0};

        mRecyclerViewGraph.getLocationOnScreen(coordinates);

        startPositionOfGraphView = coordinates[1];

        endPositionOfGraph = coordinates[1] + heightOfGraphArea;

        heightOfLabelTextInPixels = getResources().getDimensionPixelSize(R.dimen.graph_y_axis_label_size);

//        getStatusBarTitleBarHeight();

        Log.d("MyGraph", "--------- heightOfGraphArea = " + heightOfGraphArea);

        Log.d("MyGraph", "--------- heightOfEmptyArea = " + viewEmptyArea.getHeight());

        Log.d("MyGraph", "--------- startPositionOfGraphView = " + startPositionOfGraphView);

        Log.d("MyGraph", "--------- endPositionOfGraph = " + endPositionOfGraph);

        Log.d("MyGraph", "---------- getTop = " + mRecyclerViewGraph.getTop());

        Log.d("MyGraph", "---------- getBottom = " + mRecyclerViewGraph.getBottom());

        Log.d("MyGraph", "---------- value in px = " + heightOfLabelTextInPixels);

        calculateYLabelPositions();

        setUpRecyclerView();

        drawYAxisLabel();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Private methods
    ///////////////////////////////////////////////////////////////////////////

    private void initUI() {

        // linear layout for all the Y axis labels
        linearLayoutYAxisLabel = (LinearLayout) findViewById(R.id.linear_layout_y_axis_label);
//        linearLayoutYAxisLabel.getViewTreeObserver().addOnGlobalLayoutListener(this);

        mRecyclerViewGraph = (RecyclerView) findViewById(R.id.recycler_view_holding_graph);

        // Add view tree observer to get the dimensions
        mRecyclerViewGraph.getViewTreeObserver().addOnGlobalLayoutListener(this);

        mRecyclerViewMonth = (RecyclerView) findViewById(R.id.recycler_view_holding_month);

        viewEmptyArea = (View) findViewById(R.id.view_empty_area);
    }

    private void getBundleData() {

        graphType = getIntent().getExtras().getString(GraphTypeSelectionActivity.GRAPH_TYPE);

        graphData = (ArrayList<?>) getIntent().getSerializableExtra(GraphTypeSelectionActivity.GRAPH_DATA);
    }

    private void setUpRecyclerView() {

        mGraphViewAdapter = new GraphViewAdapter(this,
                graphData,
                heightOfGraphArea,
                (int) startPositionOfGraphView,
                (int) endPositionOfGraph,
                GraphUtils.getMaxValue(graphType),
                0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        mRecyclerViewGraph.setLayoutManager(linearLayoutManager);

        mRecyclerViewGraph.setAdapter(mGraphViewAdapter);

        mMonthViewAdapter = new GraphMonthAdapter(this, graphData);

        LinearLayoutManager linearLayoutManagerMonth = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        mRecyclerViewMonth.setLayoutManager(linearLayoutManagerMonth);

        mRecyclerViewMonth.setAdapter(mMonthViewAdapter);

        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mMonthViewAdapter);
        mRecyclerViewMonth.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        mRecyclerViewMonth.addItemDecoration(new DividerDecoration(this));

        setRecyclerViewInteraction();
    }

    private void setRecyclerViewInteraction() {

        mRecyclerViewGraph.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mRecyclerViewMonth.onTouchEvent(event);
                        return false;
                }
                mRecyclerViewMonth.onTouchEvent(event);
                return false;
            }
        });

        mRecyclerViewMonth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mRecyclerViewGraph.onTouchEvent(event);
                return false;
            }
        });
    }



    private void calculateYLabelPositions() {

        int totalYAxisLabelDrawableHeight =
                heightOfGraphArea - (Y_AXIS_LABEL_TOP_OFFSET);

        // subtract the height of the text size that will be rendered at the Y - Axis
//        totalYAxisLabelDrawableHeight = totalYAxisLabelDrawableHeight -
//                (GraphUtils.getYAxisLabelCount(graphType) * heightOfLabelTextInPixels);

        Log.d("MyGraph", "---------- totalYAxisLabelDrawableHeight = " + totalYAxisLabelDrawableHeight);

//        int yAxisLabelStartPosition = (int) (startPositionOfGraphView + Y_AXIS_LABEL_BOTTOM_OFFSET);

        equalInterval = totalYAxisLabelDrawableHeight / GraphUtils.getYAxisLabelCount(graphType);

//        arrayListYAxisLabelPosition = new ArrayList<>();

        // first add the first position from where the 1st label will be drawn
//        arrayListYAxisLabelPosition.add(yAxisLabelStartPosition);

        /*for (int i = 0; i < Y_AXIS_LABEL_COUNT_STEPS; i++) {

            Log.d("MyGraph", "-------arrayListYAxisLabelPosition(" + i + ")" + yAxisLabelStartPosition);

            arrayListYAxisLabelPosition.add(yAxisLabelStartPosition + (equalInterval * (i)));

            Log.d("MyGraph", "-------arrayListYAxisLabelPosition(" + i + ")" +
                    yAxisLabelStartPosition + (equalInterval * (i + 1)));
        }*/
    }


    private void drawYAxisLabel() {

        int yAxisLabelCount = GraphUtils.getYAxisLabelCount(graphType);

        ArrayList<Integer> yAxisLabel = GraphUtils.getUniformDistribution(0, GraphUtils.getMaxValue(graphType),
                yAxisLabelCount);

        for (int i = 0; i < yAxisLabelCount; i++) {

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_layout_y_axis_label);

            TextView textViewLabel = (TextView) getLayoutInflater().inflate(R.layout.y_axis_label, null);

            textViewLabel.setId(i);

            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (i == 0) {

                layoutParams.setMargins(0, Y_AXIS_LABEL_TOP_OFFSET, 0, 0);

            } else {

                layoutParams.setMargins(0, equalInterval - getResources().getDimensionPixelSize(R.dimen.height_y_axis_label_textview)/*heightOfLabelTextInPixels*//*(textViewLabel.getLineHeight())*/, 0, 0);
            }

            textViewLabel.setLayoutParams(layoutParams);

            linearLayout.addView(textViewLabel);

            Log.d("MyGraph", "-------- height of textview = " + textViewLabel.getHeight());

            if (TextUtils.equals(graphType, GraphTypeSelectionActivity.TYPE_STEPS) ) {

                textViewLabel.setText(String.valueOf(yAxisLabel.get(i)));

            } else if (TextUtils.equals(graphType, GraphTypeSelectionActivity.TYPE_MOOD)) {

                textViewLabel.setText(getResources().getStringArray(R.array.array_mood)[i]);
            }
        }
    }

    private void getStatusBarTitleBarHeight() {

        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight= contentViewTop - statusBarHeight;

        Log.i("MyGraph", "--------StatusBar Height= " + statusBarHeight + " , TitleBar Height = " + titleBarHeight);
    }
}
