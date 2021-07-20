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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class CompletionAdapter_PhotosFolder extends ArrayAdapter<CompletionModelImages> {

    Context context;
    ViewHolder viewHolder;
    ArrayList<CompletionModelImages> al_menu = new ArrayList<>();
    private SparseBooleanArray mSparseBooleanArray;


    public CompletionAdapter_PhotosFolder(Context context, ArrayList<CompletionModelImages> al_menu) {
        super(context, R.layout.activity_completion_adapter_photosfolder, al_menu);
        this.al_menu = al_menu;
        this.context = context;
        mSparseBooleanArray = new SparseBooleanArray();
    }

 /*   CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
        }
    };*/


/*    public ArrayList<CompletionModelImages> getCheckedItems() {
        ArrayList<CompletionModelImages> mTempArry = new ArrayList<CompletionModelImages>();

        for(int i=0;i<al_menu.size();i++) {
            if(mSparseBooleanArray.get(i)) {
                mTempArry.add(al_menu.get(i));
            }
        }

        return mTempArry;
    }*/


    @Override
    public int getCount() {

        Log.e("ADAPTER LIST SIZE", al_menu.size() + "");
        return al_menu.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.size() > 0) {
            return al_menu.size();
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_completion_adapter_photosfolder, parent, false);
            viewHolder.tv_foldern = (TextView) convertView.findViewById(R.id.tv_folder);
            viewHolder.tv_foldersize = (TextView) convertView.findViewById(R.id.tv_folder2);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);

            //viewHolder.check_1 = (CheckBox) convertView.findViewById(R.id.checkBox1);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            viewHolder.tv_foldern.setText(al_menu.get(position).getStr_folder());
            viewHolder.tv_foldersize.setText(al_menu.get(position).getAl_imagepath().size()+"");

        //viewHolder.checkBox.setOnCheckedChangeListener(mCheckedChangeListener);



        Glide.with(context).load("file://" + al_menu.get(position).getAl_imagepath().get(0))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.iv_image);


        return convertView;

    }


    private static class ViewHolder {
        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;
        //CheckBox check_1;
    }


}
