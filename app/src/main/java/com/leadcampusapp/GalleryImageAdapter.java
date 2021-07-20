package com.leadcampusapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

//import com.android.sripad.leadnew_22_6_2018.R;

import java.util.ArrayList;

/**
 * Created by anupamchugh on 24/10/15.
 */
public class GalleryImageAdapter extends BaseAdapter
{
    private Context mContext;
    /*public Integer[] mImageIds = {
            R.drawable.n1,
            R.drawable.n2,
            R.drawable.n3,
            R.drawable.n4,
            R.drawable.n5,
    };*/

    public ArrayList<Bitmap> mBitmapList;



    public GalleryImageAdapter(Context context,ArrayList<Bitmap> bitmapList)
    {
        mContext = context;
        mBitmapList = bitmapList;
    }

   /* public int getCount() {
        return mImageIds.length;
    }*/

    public int getCount() {
        return mBitmapList.size();
    }


    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup)
    {
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);

        //i.setImageResource(mImageIds[index]);

        i.setImageBitmap(mBitmapList.get(index));

        i.setLayoutParams(new Gallery.LayoutParams(300, 300));

        i.setScaleType(ImageView.ScaleType.FIT_XY);
     //   i.setScaleType(ImageView.ScaleType.FIT_START);


        return i;
    }



}

