package com.leadcampusapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/25/2018.
 */

public class GridAdapterPMFour extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

                /*    String UnapprovedCount;
                    String ComplitedCount;
                    String ApprovedCount;
                    String RequestForCompletion;
                    String RequestForModification;
                    String Rejected;
                    String str_studentcount;
                    String str_collegecount;*/

   /* public GridAdapterPMSecond(LayoutInflater mInflater, String unapprovedCount, String complitedCount, String approvedCount, String requestForCompletion, String requestForModification, String rejected, String str_studentcount, String str_collegecount) {
        this.mInflater = mInflater;
        UnapprovedCount = unapprovedCount;
        ComplitedCount = complitedCount;
        ApprovedCount = approvedCount;
        RequestForCompletion = requestForCompletion;
        RequestForModification = requestForModification;
        Rejected = rejected;
        this.str_studentcount = str_studentcount;
        this.str_collegecount = str_collegecount;
    }*/

    public GridAdapterPMFour(Context context){//, String unapprovedCount, String complitedCount, String approvedCount, String requestForCompletion, String requestForModification, String rejected, String str_studentcount, String str_collegecount) {
        mInflater = LayoutInflater.from(context);

     //   mItems.add(new Item("Fees", R.drawable.fees_new, "#ffbc00"));
        mItems.add(new Item("ML & LA", R.drawable.mlal_new, "#ffbc00"));
        mItems.add(new Item("Reports", R.drawable.report_new, "#f42a2a"));
        mItems.add(new Item("Contact Us",R.drawable.contact_new,"#1e7145"));

       /* mItems.add(new Item("News Feeds", R.drawable.newsfeeds_new, "#0a64c5"));
        mItems.add(new Item("Events", R.drawable.events_new, "#84795d"));
        mItems.add(new Item("Contact Us",R.drawable.contact_new,"#f42a2a"));*/

      /*  mItems.add(new Item("News Feeds",       R.drawable.newsfeeds_new,"#ffbc00"));
        mItems.add(new Item("Events",   R.drawable.events_new,"#d94343"));
        mItems.add(new Item("Contact Us",   R.drawable.contact_new,"#1e7145"));*/

      /*  UnapprovedCount = unapprovedCount;
        ComplitedCount = complitedCount;
        ApprovedCount = approvedCount;
        RequestForCompletion = requestForCompletion;
        RequestForModification = requestForModification;
        Rejected = rejected;
        this.str_studentcount = str_studentcount;
        this.str_collegecount = str_collegecount;*/
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, final View view, ViewGroup viewGroup) {
        View v = view;
        ImageView imageView;
        final TextView name;
        RelativeLayout relativeLayout;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item_pm, viewGroup, false);
            v.setTag(R.id.imageView, v.findViewById(R.id.imageView));
            v.setTag(R.id.text, v.findViewById(R.id.text));
            v.setTag(R.id.relativeLayout, v.findViewById(R.id.relativeLayout));
        }

        imageView = (ImageView) v.getTag(R.id.imageView);
        name = (TextView) v.getTag(R.id.text);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);

        Item item = getItem(i);

        imageView.setImageResource(item.drawableId);
        name.setText(item.name);
        relativeLayout.setBackgroundColor(Color.parseColor(item.color));

        relativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              //  Toast.makeText(v.getContext(), name.getText(), Toast.LENGTH_SHORT).show();
                String itemName=name.getText().toString();

                //  Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();

             /*   if(itemName.equals("Fees")){
                    Intent itthomeToFeesPaid = new Intent(v.getContext(),TShirtPaidActivity.class);
                    itthomeToFeesPaid.putExtra("From","Manager");
                    v.getContext().startActivity(itthomeToFeesPaid);
                }
                if(itemName.equals("ML & LA")){
                    Intent itthomeToAboutUs = new Intent(v.getContext(),RequestActivity.class);
                    itthomeToAboutUs.putExtra("From","Manager");
                    v.getContext().startActivity(itthomeToAboutUs);
                }*/
                if(itemName.equals("ML & LA")){
                    Intent itthomeToAboutUs = new Intent(v.getContext(),RequestActivity.class);
                    itthomeToAboutUs.putExtra("From","Manager");
                    v.getContext().startActivity(itthomeToAboutUs);
                }
                if(itemName.equals("Reports")){
                    //Log.i("tag","UnapprovedCount"+UnapprovedCount);
                    Intent i = new Intent(v.getContext(),AnalyticChartActivity.class);
                   /* i.putExtra("UnapprovedCount",UnapprovedCount);
                    i.putExtra("ComplitedCount",ComplitedCount);
                    i.putExtra("ApprovedCount",ApprovedCount);
                    i.putExtra("RequestForCompletion",RequestForCompletion);
                    i.putExtra("RequestForModification",RequestForModification);
                    i.putExtra("Rejected",Rejected);
                    i.putExtra("str_studentcount",str_studentcount);
                    i.putExtra("str_collegecount",str_collegecount);*/
                    v.getContext().startActivity(i);
                }
                /*if(itemName.equals("News Feeds")){
                    Intent itthomeToNewsFeeds = new Intent(v.getContext(),NewsFeedsActivity.class);
                    v.getContext().startActivity(itthomeToNewsFeeds);
                }
                if(itemName.equals("Events")){
                    Intent itthomeToEvent = new Intent(v.getContext(),EventsActivity.class);
                    v.getContext().startActivity(itthomeToEvent);
                }*/
                if(itemName.equals("Contact Us")){
                    Intent itthomeToAboutUs = new Intent(v.getContext(),ContactUsActivity.class);
                    itthomeToAboutUs.putExtra("From","Manager");
                    v.getContext().startActivity(itthomeToAboutUs);
                }

            }
        }
        );
        return v;
    }


    private static class Item {
        public final String name;
        public final int drawableId;
        public final String color;

        Item(String name, int drawableId, String c) {
            this.name = name;
            this.drawableId = drawableId;
            this.color=c;
        }
    }
}