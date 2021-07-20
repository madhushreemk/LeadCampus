package com.leadcampusapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

//import com.android.sripad.leadnew_22_6_2018.R;

import java.util.ArrayList;

/**
 * Created by Admin on 28-05-2018.
 */

public class CurrentEventAdapter extends BaseAdapter {

    public ArrayList<CurrentEventsModel> currentEventList;
    Activity activity;

    public CurrentEventAdapter(Activity activity, ArrayList<CurrentEventsModel> currentEventList) {
        super();
        this.activity = activity;
        this.currentEventList = currentEventList;
    }

    @Override
    public int getCount() {
        return currentEventList.size();
    }

    @Override
    public Object getItem(int position) {
        return currentEventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView mcurrent_eventImg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.currentevent_listrow, null);
            holder = new ViewHolder();

            holder.mcurrent_eventImg = (ImageView) convertView.findViewById(R.id.img_currentEvent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CurrentEventsModel item = currentEventList.get(position);

        holder.mcurrent_eventImg.setImageResource(item.getEventImg());

        //holder.mleadFundedStatus.setText(item.getLeadFundedStatus().toString());


        return convertView;
    }
}
