package com.kovacnet.waterlevel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by anzek on 13. 01. 2016.
 */
public class MeasurementRecyclerAdapter extends RecyclerView.Adapter<MeasurementRecyclerAdapter.CustomViewHolder> {
    private List<Measurement> feedItemList;
    private Context mContext;

    public MeasurementRecyclerAdapter(Context context, List<Measurement> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Measurement feedItem = feedItemList.get(i);

        //Setting text view title
        customViewHolder.level.setText(feedItem.getWaterLevel() + "cm");
        customViewHolder.timeAgo.setText(TimeAgo.getTimeAgo((long)Double.parseDouble(feedItem.getDatetime())));

        int progress =(int)((Double.parseDouble(feedItem.getBattery()) / 5)*100);
        Log.w("TAG","Stanje baterije:"+String.valueOf(progress));
        customViewHolder.progressBar.setProgress(progress);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ProgressBar progressBar;
        protected TextView level,timeAgo;

        public CustomViewHolder(View view) {
            super(view);
            this.timeAgo = (TextView) view.findViewById(R.id.timeAgo);
            this.level = (TextView) view.findViewById(R.id.level);
            this.progressBar = (ProgressBar)view.findViewById(R.id.batteryLevel);
        }
    }
}

