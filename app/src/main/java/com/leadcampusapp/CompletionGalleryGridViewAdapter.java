package com.leadcampusapp;

/**
 * Created by deepshikha on 3/3/17.
 */

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class CompletionGalleryGridViewAdapter extends ArrayAdapter<CompletionModelImages> {
    Context context;
    ViewHolder viewHolder;
    ArrayList<CompletionModelImages> al_menu = new ArrayList<>();
    int int_position;
    private SparseBooleanArray mSparseBooleanArray;
    private boolean barray[];


    public CompletionGalleryGridViewAdapter(Context context, ArrayList<CompletionModelImages> al_menu, int int_position) {
        super(context, R.layout.activity_completion_adapter_photosfolder, al_menu);
        this.al_menu = al_menu;
        this.context = context;
        this.int_position = int_position;
        mSparseBooleanArray = new SparseBooleanArray();
        barray = new boolean[al_menu.get(int_position).getAl_imagepath().size()];
    }

  /*  CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
        }
    };*/

    public ArrayList<String> getCheckedItems() {
        ArrayList<String> mTempArry = new ArrayList<String>();

        for(int i=0;i<al_menu.get(int_position).getAl_imagepath().size();i++) {
            Log.d("sparseboolarrayval:", String.valueOf(mSparseBooleanArray.get(i)));
            if(mSparseBooleanArray.get(i)) {
                Log.d("sparseboolarrayval22:", String.valueOf(mSparseBooleanArray.get(i)));
                mTempArry.add(al_menu.get(int_position).getAl_imagepath().get(i));
            }
        }

        return mTempArry;
    }

    @Override
    public int getCount() {

        Log.e("ADAPTER LIST SIZE", al_menu.get(int_position).getAl_imagepath().size() + "");
        return al_menu.get(int_position).getAl_imagepath().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.get(int_position).getAl_imagepath().size() > 0) {
            return al_menu.get(int_position).getAl_imagepath().size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_completion_adapter_photosfolders, parent, false);
            viewHolder.tv_foldern = (TextView) convertView.findViewById(R.id.tv_folder);
            viewHolder.tv_foldersize = (TextView) convertView.findViewById(R.id.tv_folder2);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);

            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("IsCheckedssskddf", String.valueOf(isChecked));
                    //mSparseBooleanArray.put(buttonView.getId(), isChecked);
                    mSparseBooleanArray.put(position, isChecked);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_foldern.setVisibility(View.GONE);
        viewHolder.tv_foldersize.setVisibility(View.GONE);



        Glide.with(context).load("file://" + al_menu.get(int_position).getAl_imagepath().get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.iv_image);


        return convertView;

    }

    private static class ViewHolder {
        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;
        CheckBox checkBox;
    }


}
