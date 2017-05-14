package com.pfizer.droidfactory.mygraph.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pfizer.droidfactory.mygraph.R;
import com.pfizer.droidfactory.mygraph.model.GraphMoodModel;
import com.pfizer.droidfactory.mygraph.model.GraphStepsModel;
import com.pfizer.droidfactory.mygraph.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class GraphViewAdapter extends RecyclerView.Adapter<GraphViewAdapter.GraphViewHolder> {

    private Context mContext;

    private ArrayList<?> horizontalDataList;

    private int heightOfGraphArea;

    private int startPositionOfGraph;

    private int endPositionOfGraph;

    private int heightOfHorizontalGridLineInPixels;

    private int equalInterval;

    private int maxValue;

    private int minValue;

    public GraphViewAdapter(Context context,
                     ArrayList<?> horizontalDataList,
                     int heightOfGraphArea,
                     int startPositionOfGraph,
                     int endPositionOfGraph,
                     int maxValue,
                     int minValue) {

        this.mContext = context;

        this.horizontalDataList = horizontalDataList;

        this.heightOfGraphArea = heightOfGraphArea;

        this.startPositionOfGraph = startPositionOfGraph;

        this.endPositionOfGraph = endPositionOfGraph;

        this.maxValue = maxValue;

        this.minValue = minValue;

        calculateHorizontalGridPositions();
    }

    @Override
    public GraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bigger_graph_single_item, parent, false);

        return new GraphViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GraphViewHolder holder, int position) {

//        ((GraphViewHolder) holder).drawHorizontalGridLines();
        ((GraphViewHolder) holder).drawBarAndReflection(position);
        holder.setDate(position);
    }

    @Override
    public int getItemCount() {
        return horizontalDataList.size();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Private methods
    ///////////////////////////////////////////////////////////////////////////

    private void calculateHorizontalGridPositions() {

        heightOfHorizontalGridLineInPixels = mContext.getResources().getDimensionPixelSize(R.dimen.dp_1);

        int totalBarGraphDrawableArea = heightOfGraphArea - MainActivity.Y_AXIS_LABEL_TOP_OFFSET;

        totalBarGraphDrawableArea = totalBarGraphDrawableArea - (MainActivity.Y_AXIS_LABEL_COUNT_STEPS * heightOfHorizontalGridLineInPixels);

        equalInterval = totalBarGraphDrawableArea / MainActivity.Y_AXIS_LABEL_COUNT_STEPS;
    }

    private int getBarHeight(int dayValue) {

        return (((endPositionOfGraph - MainActivity.Y_AXIS_LABEL_TOP_OFFSET ) -
                startPositionOfGraph) * dayValue) / maxValue;
    }


    ///////////////////////////////////////////////////////////////////////////
    // View Holder class for recycler view
    ///////////////////////////////////////////////////////////////////////////

    class GraphViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayoutBarRange;

        View viewBarGraph;

        ImageView imageViewBarReflection;

        TextView textViewDate;

        GraphViewHolder(View itemView) {

            super(itemView);

            initUIComponents();

//            drawHorizontalGridLines();
        }

        private void initUIComponents() {

//            linearLayoutBarRange = (LinearLayout) itemView.findViewById(R.id.linear_layout_bar_range);

            viewBarGraph = (View) itemView.findViewById(R.id.view_bar_graph);

            imageViewBarReflection = (ImageView) itemView.findViewById(R.id.image_view_bar_shadow);

            textViewDate = (TextView) itemView.findViewById(R.id.text_view_date);
        }

        private void drawHorizontalGridLines() {

            for (int i = 0; i < MainActivity.Y_AXIS_LABEL_COUNT_STEPS - 1; i++) {

//                LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout_bar_range);

                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View viewHorizontalGridLine = layoutInflater.inflate(R.layout.horizontal_grid_line, null);

                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                if (i == 0) {

                    layoutParams.setMargins(0, MainActivity.Y_AXIS_LABEL_TOP_OFFSET, 0, 0);

                } else {

                    layoutParams.setMargins(0, equalInterval, 0, 0);
                }

                viewHorizontalGridLine.setLayoutParams(layoutParams);

//                linearLayout.addView(viewHorizontalGridLine);
            }

        }

        private void drawBarAndReflection(int position) {

//            int heightOfBar = getBarHeight(horizontalDataList.get(position));

            int heightOfBar = 0;

            Object o = horizontalDataList.get(position);

            if (o instanceof GraphStepsModel) {
                heightOfBar = getBarHeight(((GraphStepsModel) o).getStepsCount());
            } else if (o instanceof GraphMoodModel) {
                heightOfBar = getBarHeight(((GraphMoodModel) o).getMoodLevel());
            }

            viewBarGraph.getLayoutParams().height = heightOfBar;

            if (heightOfBar == 0) {

                imageViewBarReflection.setVisibility(View.INVISIBLE);

            } else {

                imageViewBarReflection.setVisibility(View.VISIBLE);
            }
        }

        private void setDate(int position) {

            Object o = horizontalDataList.get(position);

            String dateAndMonth = "";

            if (o instanceof GraphStepsModel) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(((GraphStepsModel) o).getDate());
                int date = calendar.get(Calendar.DATE);

                String month = new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime());

                dateAndMonth = date + System.getProperty ("line.separator") + month;

            } else if (o instanceof GraphMoodModel) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(((GraphMoodModel) o).getDate());
                int date = calendar.get(Calendar.DATE);

                String month = new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime());

                dateAndMonth = date + System.getProperty ("line.separator") + month;
            }

            textViewDate.setText(dateAndMonth);
        }
    }
}
