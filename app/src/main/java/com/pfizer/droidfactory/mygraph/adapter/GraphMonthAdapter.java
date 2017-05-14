package com.pfizer.droidfactory.mygraph.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfizer.droidfactory.mygraph.R;
import com.pfizer.droidfactory.mygraph.model.GraphStepsModel;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 3/15/2017.
 */

public class GraphMonthAdapter extends RecyclerView.Adapter<GraphMonthAdapter.MonthViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    Context mContext;

    ArrayList<?> arrayListMonthData;

    String month;

    public GraphMonthAdapter(Context context, ArrayList<?> arrayListMonthData) {
        this.mContext = context;
        this.arrayListMonthData = arrayListMonthData;
    }

    @Override
    public GraphMonthAdapter.MonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_month_name, parent, false);
        return new MonthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GraphMonthAdapter.MonthViewHolder holder, int position) {
        holder.setMonthName(position);
    }

    @Override
    public long getHeaderId(int i) {
        Object o = arrayListMonthData.get(i);
        GraphStepsModel stepsModel;
        Calendar calendar = Calendar.getInstance();

        if (o instanceof GraphStepsModel) {

            stepsModel = (GraphStepsModel)arrayListMonthData.get(i);

            calendar.setTime(stepsModel.getDate());

            month = new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime());
        }
        return month.charAt(0)
                + month.charAt(1)
                + month.charAt(2);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_month_name, viewGroup, false);
        return new MonthHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((MonthHeaderViewHolder)viewHolder).setTextViewMonthItemHeader(i);
    }

    @Override
    public int getItemCount() {
        return arrayListMonthData.size();
    }

    class MonthViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMonthItem;

        MonthViewHolder(View itemView) {
            super(itemView);

            textViewMonthItem = (TextView) itemView.findViewById(R.id.text_view_month);
        }

        private void setMonthName(int position) {
            Object o = arrayListMonthData.get(position);
            GraphStepsModel stepsModel;
            Calendar calendar = Calendar.getInstance();
            String monthName = "";


            if (o instanceof GraphStepsModel) {

                stepsModel = (GraphStepsModel)arrayListMonthData.get(position);

                calendar.setTime(stepsModel.getDate());

                monthName = new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime());
            }

            textViewMonthItem.setText(monthName);
        }
    }

    private class MonthHeaderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMonthItemHeader;

        MonthHeaderViewHolder(View itemView) {
            super(itemView);

            textViewMonthItemHeader = (TextView) itemView.findViewById(R.id.text_view_month);
        }

        void setTextViewMonthItemHeader(int position) {
            Object o = arrayListMonthData.get(position);
            GraphStepsModel stepsModel;
            Calendar calendar = Calendar.getInstance();
            String monthName = "";


            if (o instanceof GraphStepsModel) {

                stepsModel = (GraphStepsModel)arrayListMonthData.get(position);

                calendar.setTime(stepsModel.getDate());

                monthName = new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime());
            }

            textViewMonthItemHeader.setText(monthName);
        }
    }
}
