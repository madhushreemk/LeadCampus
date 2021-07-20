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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/25/2018.
 */

public class GridAdapterSecond extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public GridAdapterSecond(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item("News Feeds",       R.drawable.newsfeeds_new,"#ffbc00"));
        mItems.add(new Item("Events",   R.drawable.events_new,"#d94343"));
        mItems.add(new Item("Contact Us",   R.drawable.contact_new,"#1e7145"));
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView imageView;
        final TextView name;
        RelativeLayout relativeLayout;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
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
                                               //   Toast.makeText(v.getContext(), name.getText(), Toast.LENGTH_SHORT).show();
                                                  String itemName=name.getText().toString();
                                                  if(itemName.equals("News Feeds")){
                                                      Intent itthomeToNewsFeeds = new Intent(v.getContext(), NewsFeedsActivity.class);
                                                      //    itthomeToNewsFeeds.putExtra("From","Student");
                                                      v.getContext().startActivity(itthomeToNewsFeeds);
                                                  }
                                                  if(itemName.equals("Events")){
                                                      Intent itthomeToEvent = new Intent(v.getContext(), EventsActivity.class);
                                                      //   itthomeToEvent.putExtra("From","Student");
                                                      //Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), TestActivity2.class);
                                                      v.getContext().startActivity(itthomeToEvent);
                                                  }
                                                  if(itemName.equals("Contact Us")){
                                                      Intent itthomeToAboutUs = new Intent(v.getContext(), ContactUsActivity.class);
                                                      itthomeToAboutUs.putExtra("From","Student");
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