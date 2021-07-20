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

public class GridAdapterPrinciple extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;
    private String str_isFeesPaid = null;

    public String getStr_isFeesPaid() {
        return str_isFeesPaid;
    }

    public void setStr_isFeesPaid(String str_isFeesPaid) {
        this.str_isFeesPaid = str_isFeesPaid;
    }

    public GridAdapterPrinciple(Context context) {

        mInflater = LayoutInflater.from(context);
        //str_isFeesPaids = str_isFeesPaid;

        mItems.add(new Item("About LEAD",       R.drawable.about_new,"#f58d00"));
        mItems.add(new Item("Projects",   R.drawable.project_new,"#97c024"));
        mItems.add(new Item("Stories",   R.drawable.story_nw,"#4285f4"));
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
              //  Toast.makeText(v.getContext(), name.getText(), Toast.LENGTH_SHORT).show();
                String itemName=name.getText().toString();
                if(itemName.equals("About LEAD")){
                    Intent itthomeToAboutUs = new Intent(v.getContext(), AboutUsActivity.class);
                    itthomeToAboutUs.putExtra("From","Principle");
                    v.getContext().startActivity(itthomeToAboutUs);
                }
                if(itemName.equals("Projects")){
/*                    Intent itthomeToProject = new Intent(v.getContext(), ProjectDetails.class);
                    //  itthomeToProject.putExtra("From","Student");
                    v.getContext().startActivity(itthomeToProject);*/


                        Intent itthomeToProject = new Intent(v.getContext(), PrincipleLoginActivity.class);
                        // itthomeToProject.putExtra("From","Student");
                        v.getContext().startActivity(itthomeToProject);

                }
                if(itemName.equals("Stories")){
                    Intent itthomeTostory = new Intent(v.getContext(),LeadStoryActivity.class);
                    itthomeTostory.putExtra("stroy_position", "0");
                    v.getContext().startActivity(itthomeTostory);
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